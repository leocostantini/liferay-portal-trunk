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

package com.liferay.portal.mirage.custom;

import com.sun.portal.cms.mirage.exception.CMSException;
import com.sun.portal.cms.mirage.exception.TemplateNotFoundException;
import com.sun.portal.cms.mirage.model.custom.Content;
import com.sun.portal.cms.mirage.model.custom.VersionableContent;
import com.sun.portal.cms.mirage.service.custom.ContentService;

import java.io.InputStream;
import java.util.List;

/**
 * <a href="ContentServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Prakash Reddy
 *
 */
public class ContentServiceImpl implements ContentService {

    public void checkinContent(Content content) 
            throws CMSException, TemplateNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public VersionableContent checkoutContent(
                String contentName, String contentTypeUUID) 
            throws CMSException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void createContent(Content content) 
            throws CMSException, TemplateNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void deleteContent(String contentName, String contentTypeUUID) 
            throws CMSException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Content getContentByNameAndType(String contentName, String contentTypeUUID) 
            throws CMSException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Content getContentByNameTypeNameAndCategory(
                String contentName, String contentTypeName, String categoryName)
            throws CMSException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Content getContentByUUID(String uuid) throws CMSException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public VersionableContent getContentByVersion(
                String contentName, String contentTypeUUID, String versionName) 
            throws CMSException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<String> getContentNamesByType(String contentTypeUUID) 
            throws CMSException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Content> getContentsByType(String contentTypeUUID) throws CMSException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getContentURL(String appURL, String UUID) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<String> getVersionNames(String contentName, String contentTypeUUID) 
            throws CMSException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<VersionableContent> getVersions(
                String contentName, String contentTypeUUID) 
            throws CMSException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void unCheckoutContent(String contentName, String contentTypeUUID) 
            throws CMSException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void updateContent(Content content) 
            throws CMSException, TemplateNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void updateFileField(
                String contentUUID, String fieldName, 
                InputStream updatedFileInputStream) 
            throws CMSException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}