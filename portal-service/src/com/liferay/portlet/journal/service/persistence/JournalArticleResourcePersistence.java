/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.journal.model.JournalArticleResource;

/**
 * <a href="JournalArticleResourcePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalArticleResourcePersistenceImpl
 * @see       JournalArticleResourceUtil
 * @generated
 */
public interface JournalArticleResourcePersistence extends BasePersistence<JournalArticleResource> {
	public void cacheResult(
		com.liferay.portlet.journal.model.JournalArticleResource journalArticleResource);

	public void cacheResult(
		java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> journalArticleResources);

	public com.liferay.portlet.journal.model.JournalArticleResource create(
		long resourcePrimKey);

	public com.liferay.portlet.journal.model.JournalArticleResource remove(
		long resourcePrimKey)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleResourceException;

	public com.liferay.portlet.journal.model.JournalArticleResource updateImpl(
		com.liferay.portlet.journal.model.JournalArticleResource journalArticleResource,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.journal.model.JournalArticleResource findByPrimaryKey(
		long resourcePrimKey)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleResourceException;

	public com.liferay.portlet.journal.model.JournalArticleResource fetchByPrimaryKey(
		long resourcePrimKey) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> findByGroupId(
		long groupId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.journal.model.JournalArticleResource findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleResourceException;

	public com.liferay.portlet.journal.model.JournalArticleResource findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleResourceException;

	public com.liferay.portlet.journal.model.JournalArticleResource[] findByGroupId_PrevAndNext(
		long resourcePrimKey, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleResourceException;

	public com.liferay.portlet.journal.model.JournalArticleResource findByG_A(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleResourceException;

	public com.liferay.portlet.journal.model.JournalArticleResource fetchByG_A(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.journal.model.JournalArticleResource fetchByG_A(
		long groupId, java.lang.String articleId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public void removeByG_A(long groupId, java.lang.String articleId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleResourceException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public int countByG_A(long groupId, java.lang.String articleId)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}