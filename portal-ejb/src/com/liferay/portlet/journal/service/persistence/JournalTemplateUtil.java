/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="JournalTemplateUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class JournalTemplateUtil {
	public static com.liferay.portlet.journal.model.JournalTemplate create(
		com.liferay.portlet.journal.service.persistence.JournalTemplatePK journalTemplatePK) {
		return getPersistence().create(journalTemplatePK);
	}

	public static com.liferay.portlet.journal.model.JournalTemplate remove(
		com.liferay.portlet.journal.service.persistence.JournalTemplatePK journalTemplatePK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchTemplateException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(journalTemplatePK));
		}

		com.liferay.portlet.journal.model.JournalTemplate journalTemplate = getPersistence()
																				.remove(journalTemplatePK);

		if (listener != null) {
			listener.onAfterRemove(journalTemplate);
		}

		return journalTemplate;
	}

	public static com.liferay.portlet.journal.model.JournalTemplate remove(
		com.liferay.portlet.journal.model.JournalTemplate journalTemplate)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(journalTemplate);
		}

		journalTemplate = getPersistence().remove(journalTemplate);

		if (listener != null) {
			listener.onAfterRemove(journalTemplate);
		}

		return journalTemplate;
	}

	public static com.liferay.portlet.journal.model.JournalTemplate update(
		com.liferay.portlet.journal.model.JournalTemplate journalTemplate)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = journalTemplate.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(journalTemplate);
			}
			else {
				listener.onBeforeUpdate(journalTemplate);
			}
		}

		journalTemplate = getPersistence().update(journalTemplate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(journalTemplate);
			}
			else {
				listener.onAfterUpdate(journalTemplate);
			}
		}

		return journalTemplate;
	}

	public static com.liferay.portlet.journal.model.JournalTemplate update(
		com.liferay.portlet.journal.model.JournalTemplate journalTemplate,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = journalTemplate.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(journalTemplate);
			}
			else {
				listener.onBeforeUpdate(journalTemplate);
			}
		}

		journalTemplate = getPersistence().update(journalTemplate, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(journalTemplate);
			}
			else {
				listener.onAfterUpdate(journalTemplate);
			}
		}

		return journalTemplate;
	}

	public static com.liferay.portlet.journal.model.JournalTemplate findByPrimaryKey(
		com.liferay.portlet.journal.service.persistence.JournalTemplatePK journalTemplatePK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence().findByPrimaryKey(journalTemplatePK);
	}

	public static com.liferay.portlet.journal.model.JournalTemplate fetchByPrimaryKey(
		com.liferay.portlet.journal.service.persistence.JournalTemplatePK journalTemplatePK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(journalTemplatePK);
	}

	public static java.util.List findByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List findByGroupId(long groupId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end);
	}

	public static java.util.List findByGroupId(long groupId, int begin,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalTemplate findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalTemplate findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalTemplate[] findByGroupId_PrevAndNext(
		com.liferay.portlet.journal.service.persistence.JournalTemplatePK journalTemplatePK,
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence().findByGroupId_PrevAndNext(journalTemplatePK,
			groupId, obc);
	}

	public static java.util.List findByC_T(long companyId,
		java.lang.String templateId) throws com.liferay.portal.SystemException {
		return getPersistence().findByC_T(companyId, templateId);
	}

	public static java.util.List findByC_T(long companyId,
		java.lang.String templateId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_T(companyId, templateId, begin, end);
	}

	public static java.util.List findByC_T(long companyId,
		java.lang.String templateId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_T(companyId, templateId, begin, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalTemplate findByC_T_First(
		long companyId, java.lang.String templateId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence().findByC_T_First(companyId, templateId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalTemplate findByC_T_Last(
		long companyId, java.lang.String templateId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence().findByC_T_Last(companyId, templateId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalTemplate[] findByC_T_PrevAndNext(
		com.liferay.portlet.journal.service.persistence.JournalTemplatePK journalTemplatePK,
		long companyId, java.lang.String templateId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence().findByC_T_PrevAndNext(journalTemplatePK,
			companyId, templateId, obc);
	}

	public static java.util.List findByC_G_S(long companyId, long groupId,
		java.lang.String structureId) throws com.liferay.portal.SystemException {
		return getPersistence().findByC_G_S(companyId, groupId, structureId);
	}

	public static java.util.List findByC_G_S(long companyId, long groupId,
		java.lang.String structureId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_G_S(companyId, groupId, structureId,
			begin, end);
	}

	public static java.util.List findByC_G_S(long companyId, long groupId,
		java.lang.String structureId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_G_S(companyId, groupId, structureId,
			begin, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalTemplate findByC_G_S_First(
		long companyId, long groupId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence().findByC_G_S_First(companyId, groupId,
			structureId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalTemplate findByC_G_S_Last(
		long companyId, long groupId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence().findByC_G_S_Last(companyId, groupId,
			structureId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalTemplate[] findByC_G_S_PrevAndNext(
		com.liferay.portlet.journal.service.persistence.JournalTemplatePK journalTemplatePK,
		long companyId, long groupId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence().findByC_G_S_PrevAndNext(journalTemplatePK,
			companyId, groupId, structureId, obc);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer, begin,
			end);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List findAll(int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end);
	}

	public static java.util.List findAll(int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByC_T(long companyId, java.lang.String templateId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByC_T(companyId, templateId);
	}

	public static void removeByC_G_S(long companyId, long groupId,
		java.lang.String structureId) throws com.liferay.portal.SystemException {
		getPersistence().removeByC_G_S(companyId, groupId, structureId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByC_T(long companyId, java.lang.String templateId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_T(companyId, templateId);
	}

	public static int countByC_G_S(long companyId, long groupId,
		java.lang.String structureId) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_G_S(companyId, groupId, structureId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static JournalTemplatePersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(JournalTemplatePersistence persistence) {
		_persistence = persistence;
	}

	private static JournalTemplateUtil _getUtil() {
		if (_util == null) {
			_util = (JournalTemplateUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static ModelListener _getListener() {
		if (Validator.isNotNull(_LISTENER)) {
			try {
				return (ModelListener)Class.forName(_LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		return null;
	}

	private static final String _UTIL = JournalTemplateUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.journal.model.JournalTemplate"));
	private static Log _log = LogFactory.getLog(JournalTemplateUtil.class);
	private static JournalTemplateUtil _util;
	private JournalTemplatePersistence _persistence;
}