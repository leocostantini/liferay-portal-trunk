/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.lar;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.ratings.service.RatingsEntryLocalServiceUtil;
import com.liferay.portlet.tags.NoSuchAssetException;
import com.liferay.portlet.tags.model.TagsAsset;
import com.liferay.portlet.tags.model.TagsEntry;
import com.liferay.portlet.tags.service.TagsAssetLocalServiceUtil;
import com.liferay.util.MapUtil;
import com.liferay.util.xml.XMLFormatter;

import com.thoughtworks.xstream.XStream;

import java.io.IOException;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <a href="PortletDataContextImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * Holds context information that is used during exporting adn importing portlet
 * data.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Bruno Farache
 * @author Alex Chow
 *
 */
public class PortletDataContextImpl implements PortletDataContext {

	public PortletDataContextImpl(
		long companyId, long groupId, Map<String, String[]> parameterMap,
		Set primaryKeys, UserIdStrategy userIdStrategy, ZipReader zipReader) {

		_companyId = companyId;
		_groupId = groupId;
		_parameterMap = parameterMap;
		_primaryKeys = primaryKeys;
		_dataStrategy =  MapUtil.getString(
			parameterMap, PortletDataHandlerKeys.DATA_STRATEGY,
			PortletDataHandlerKeys.DATA_STRATEGY_MIRROR);
		_userIdStrategy = userIdStrategy;
		_zipReader = zipReader;
		_zipWriter = null;
	}

	public PortletDataContextImpl(
			long companyId, long groupId, Map<String, String[]> parameterMap,
			Set primaryKeys, Date startDate, Date endDate, ZipWriter zipWriter)
		throws PortletDataException {

		_validateDateRange(startDate, endDate);

		_companyId = companyId;
		_groupId = groupId;
		_parameterMap = parameterMap;
		_primaryKeys = primaryKeys;
		_dataStrategy =  null;
		_userIdStrategy = null;
		_startDate = startDate;
		_endDate = endDate;
		_zipReader = null;
		_zipWriter = zipWriter;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public long getPlid() {
		return _plid;
	}

	public void setPlid(long plid) {
		_plid = plid;
	}

	public Map getParameterMap() {
		return _parameterMap;
	}

	public boolean getBooleanParameter(String namespace, String name) {
		boolean defaultValue = MapUtil.getBoolean(
			getParameterMap(),
			PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT, true);

		return MapUtil.getBoolean(
			getParameterMap(),
			PortletDataHandlerControl.getNamespacedControlName(namespace, name),
			defaultValue);
	}

	public Set getPrimaryKeys() {
		return _primaryKeys;
	}

	public boolean addPrimaryKey(Class<?> classObj, Object primaryKey) {
		boolean value = hasPrimaryKey(classObj, primaryKey);

		if (!value) {
			_primaryKeys.add(getPrimaryKeyString(classObj, primaryKey));
		}

		return value;
	}

	public boolean hasPrimaryKey(Class<?> classObj, Object primaryKey) {
		return _primaryKeys.contains(getPrimaryKeyString(classObj, primaryKey));
	}

	public Map getNewPrimaryKeysMap(Class<?> classObj) {
		Map map = _newPrimaryKeysMaps.get(classObj.getName());

		if (map == null) {
			map = new HashMap();

			_newPrimaryKeysMaps.put(classObj.getName(), map);
		}

		return map;
	}

	public Map getComments() {
		return _commentsMap;
	}

	public void addComments(Class<?> classObj, Object primaryKey)
		throws PortalException, SystemException {

		List messages = MBMessageLocalServiceUtil.getMessages(
			classObj.getName(), ((Long)primaryKey).longValue());

		if (messages.size() == 0) {
			return;
		}

		Iterator itr = messages.iterator();

		while (itr.hasNext()) {
			MBMessage message = (MBMessage)itr.next();

			message.setUserUuid(message.getUserUuid());
		}

		_commentsMap.put(getPrimaryKeyString(classObj, primaryKey), messages);
	}

	public void addComments(String className, Object primaryKey, List messages)
		throws PortalException, SystemException {

		_commentsMap.put(
			getPrimaryKeyString(className, primaryKey), messages);
	}

	public void importComments(
			Class<?> classObj, Object primaryKey, Object newPrimaryKey,
			long groupId)
		throws PortalException, SystemException {

		Map messagePKs = new HashMap();
		Map threadPKs = new HashMap();

		List messages = (List)_commentsMap.get(
			getPrimaryKeyString(classObj, primaryKey));

		if (messages == null) {
			return;
		}

		Iterator itr = messages.iterator();

		while (itr.hasNext()) {
			MBMessage message = (MBMessage)itr.next();

			long userId = getUserId(message.getUserUuid());
			long parentMessageId = MapUtil.getLong(
				messagePKs, message.getParentMessageId(),
				message.getParentMessageId());
			long threadId = MapUtil.getLong(
				threadPKs, message.getThreadId(), message.getThreadId());

			MBMessage newMessage =
				MBMessageLocalServiceUtil.addDiscussionMessage(
					userId, groupId, classObj.getName(),
					((Long)newPrimaryKey).longValue(), threadId,
					parentMessageId, message.getSubject(), message.getBody());

			messagePKs.put(
				message.getPrimaryKeyObj(), newMessage.getPrimaryKeyObj());
			threadPKs.put(
				new Long(message.getThreadId()),
				new Long(newMessage.getThreadId()));
		}
	}

	public Map getRatingsEntries() {
		return _ratingsEntriesMap;
	}

	public void addRatingsEntries(Class<?> classObj, Object primaryKey)
		throws PortalException, SystemException {

		List entries = RatingsEntryLocalServiceUtil.getEntries(
			classObj.getName(), ((Long)primaryKey).longValue());

		if (entries.size() == 0) {
			return;
		}

		Iterator itr = entries.iterator();

		while (itr.hasNext()) {
			RatingsEntry entry = (RatingsEntry)itr.next();

			entry.setUserUuid(entry.getUserUuid());
		}

		_ratingsEntriesMap.put(
			getPrimaryKeyString(classObj, primaryKey), entries);
	}

	public void addRatingsEntries(
			String className, Object primaryKey, List entries)
		throws PortalException, SystemException {

		_ratingsEntriesMap.put(
			getPrimaryKeyString(className, primaryKey), entries);
	}

	public void importRatingsEntries(
			Class<?> classObj, Object primaryKey, Object newPrimaryKey)
		throws PortalException, SystemException {

		List entries = (List)_ratingsEntriesMap.get(
			getPrimaryKeyString(classObj, primaryKey));

		if (entries == null) {
			return;
		}

		Iterator itr = entries.iterator();

		while (itr.hasNext()) {
			RatingsEntry entry = (RatingsEntry)itr.next();

			long userId = getUserId(entry.getUserUuid());

			RatingsEntryLocalServiceUtil.updateEntry(
				userId, classObj.getName(), ((Long)newPrimaryKey).longValue(),
				entry.getScore());
		}
	}

	public String[] getTagsEntries(Class<?> classObj, Object primaryKey) {
		return (String[])_tagsEntriesMap.get(
			getPrimaryKeyString(classObj, primaryKey));
	}

	public String[] getTagsEntries(String className, Object primaryKey) {
		return (String[])_tagsEntriesMap.get(
			getPrimaryKeyString(className, primaryKey));
	}

	public Map getTagsEntries() {
		return _tagsEntriesMap;
	}

	public void addTagsEntries(Class<?> classObj, Object classPK)
		throws PortalException, SystemException {

		TagsAsset tagsAsset = null;

		try {
            tagsAsset = TagsAssetLocalServiceUtil.getAsset(
            	classObj.getName(), ((Long)classPK).longValue());
        }
        catch (NoSuchAssetException nsae) {

			// LEP-4979

			return;
		}

		List tagsEntriesList = tagsAsset.getEntries();

		if (tagsEntriesList.size() == 0) {
			return;
		}

		String[] tagsEntries = new String[tagsEntriesList.size()];

		Iterator itr = tagsEntriesList.iterator();

		for (int i = 0; itr.hasNext(); i++) {
			TagsEntry tagsEntry = (TagsEntry)itr.next();

			tagsEntries[i] = tagsEntry.getName();
		}

		_tagsEntriesMap.put(
			getPrimaryKeyString(classObj, classPK), tagsEntries);
	}

	public void addTagsEntries(
			String className, Object classPK, String[] values)
		throws PortalException, SystemException {

		_tagsEntriesMap.put(getPrimaryKeyString(className, classPK), values);
	}

	public String getDataStrategy() {
		 return _dataStrategy;
	}

	public UserIdStrategy getUserIdStrategy() {
		return _userIdStrategy;
	}

	public long getUserId(String userUuid) throws SystemException {
		return _userIdStrategy.getUserId(userUuid);
	}

	public Date getStartDate() {
		return _startDate;
	}

	public Date getEndDate() {
		return _endDate;
	}

	public boolean hasDateRange() {
		if (_startDate != null) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isWithinDateRange(Date modifiedDate) {
		if (!hasDateRange()) {
			return true;
		}
		else if ((_startDate.compareTo(modifiedDate) <= 0) &&
				 (_endDate.after(modifiedDate))) {

			return true;
		}
		else {
			return false;
		}
	}

	public String getLayoutPath(long layoutId) {
		return getRootPath() + LAYOUTS_ROOT_PATH + layoutId;
	}

	public String getPortletPath(String portletId) {
		return getRootPath() + PORTLETS_ROOT_PATH + portletId;
	}

	public String getRootPath() {
		return GROUPS_ROOT_PATH + getGroupId();
	}

	public void addZipEntry(String path, byte[] bytes) throws SystemException {
		try {
			getZipWriter().addEntry(path, bytes);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public void addZipEntry(String path, Object object) throws SystemException {
		addZipEntry(path, toXMLFormatted(object));
	}

	public void addZipEntry(String path, String s) throws SystemException {
		try {
			getZipWriter().addEntry(path, s);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public void addZipEntry(String path, StringMaker sm) throws SystemException {
		try {
			getZipWriter().addEntry(path, sm);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public Map<String,byte[]> getZipEntries() {
		return getZipReader().getEntries();
	}

	public byte[] getZipEntryAsByteArray(String path) {
		return getZipReader().getEntryAsByteArray(path);
	}

	public Object getZipEntryAsObject(String path) {
		return fromXML(getZipEntryAsString(path));
	}

	public String getZipEntryAsString(String path) {
		return getZipReader().getEntryAsString(path);
	}
	public Map<String,List<ObjectValuePair<String,byte[]>>>
			getZipFolderEntries() {

		return getZipReader().getFolderEntries();
	}
	public List<ObjectValuePair<String,byte[]>> getZipFolderEntries(
			String path) {
		return getZipReader().getFolderEntries(path);
	}

	public ZipReader getZipReader() {
		return _zipReader;
	}

	public ZipWriter getZipWriter() {
		return _zipWriter;
	}

	protected String getPrimaryKeyString(Class<?> classObj, Object primaryKey) {
		return getPrimaryKeyString(classObj.getName(), primaryKey);
	}

	protected String getPrimaryKeyString(String className, Object primaryKey) {
		StringMaker sm = new StringMaker();

		sm.append(className);
		sm.append(StringPool.POUND);
		sm.append(primaryKey);

		return sm.toString();
	}

	public Object fromXML(byte[] bytes) {
		return _xStream.fromXML(new String(bytes));
	}

	public Object fromXML(String xml) {
		return _xStream.fromXML(xml);
	}

	public String toXML(Object object) {
		return _xStream.toXML(object);
	}

	public String toXMLFormatted(Object object)
		throws SystemException {

		try {
			return XMLFormatter.toString(_xStream.toXML(object));
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	private void _validateDateRange(Date startDate, Date endDate)
		throws PortletDataException {

		if ((startDate == null) ^ (endDate == null)) {
			throw new PortletDataException(
				"Both start and end dates must have valid values or be null");
		}

		if (startDate != null) {
			if (startDate.after(endDate) || startDate.equals(endDate)) {
				throw new PortletDataException(
					"The start date cannot be after the end date");
			}

			Date now = new Date();

			if (startDate.after(now) || endDate.after(now)) {
				throw new PortletDataException(
					"Dates must not be in the future");
			}
		}
	}

	private long _companyId;
	private long _groupId;
	private long _plid;
	private Map _commentsMap = new HashMap();
	private Map<String, String[]> _parameterMap;
	private Map _ratingsEntriesMap = new HashMap();
	private Map _tagsEntriesMap = new HashMap();
	private Set _primaryKeys;
	private Map<String, Map> _newPrimaryKeysMaps = new HashMap<String, Map>();
	private String _dataStrategy;
	private UserIdStrategy _userIdStrategy;
	private Date _startDate;
	private Date _endDate;
	private XStream _xStream = new XStream();
	private ZipReader _zipReader;
	private ZipWriter _zipWriter;

}