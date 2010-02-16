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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.UserIdMapper;
import com.liferay.portal.service.base.UserIdMapperLocalServiceBaseImpl;

import java.util.List;

/**
 * <a href="UserIdMapperLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class UserIdMapperLocalServiceImpl
	extends UserIdMapperLocalServiceBaseImpl {

	public void deleteUserIdMappers(long userId) throws SystemException {
		userIdMapperPersistence.removeByUserId(userId);
	}

	public UserIdMapper getUserIdMapper(long userId, String type)
		throws PortalException, SystemException {

		return userIdMapperPersistence.findByU_T(userId, type);
	}

	public UserIdMapper getUserIdMapperByExternalUserId(
			String type, String externalUserId)
		throws PortalException, SystemException {

		return userIdMapperPersistence.findByT_E(type, externalUserId);
	}

	public List<UserIdMapper> getUserIdMappers(long userId)
		throws SystemException {

		return userIdMapperPersistence.findByUserId(userId);
	}

	public UserIdMapper updateUserIdMapper(
			long userId, String type, String description, String externalUserId)
		throws SystemException {

		UserIdMapper userIdMapper = userIdMapperPersistence.fetchByU_T(
			userId, type);

		if (userIdMapper == null) {
			long userIdMapperId = counterLocalService.increment();

			userIdMapper = userIdMapperPersistence.create(userIdMapperId);
		}

		userIdMapper.setUserId(userId);
		userIdMapper.setType(type);
		userIdMapper.setDescription(description);
		userIdMapper.setExternalUserId(externalUserId);

		userIdMapperPersistence.update(userIdMapper, false);

		return userIdMapper;
	}

}