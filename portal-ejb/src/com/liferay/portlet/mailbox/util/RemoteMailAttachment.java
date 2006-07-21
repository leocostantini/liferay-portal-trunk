/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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
package com.liferay.portlet.mailbox.util;

import com.liferay.util.StringUtil;

/**
 * <a href="RemoteMailAttachment.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Alexander Chow
 *
 */
public class RemoteMailAttachment {

	private static String FOLDER_SEPARATOR = "_MAIL_FOLDER_";

	private static String MESSAGE_SEPARATOR = "_MAIL_MESSAGE_";

	public static String buildContentPath(String folderName, long messageUID) {
		return folderName + FOLDER_SEPARATOR + messageUID + MESSAGE_SEPARATOR;
	}

	public static String [] parsePath(String contentPath) {
		String [] path = new String[3];

		String postFolder = StringUtil.split(contentPath, FOLDER_SEPARATOR)[1];
		String [] msgWrap = StringUtil.split(postFolder, MESSAGE_SEPARATOR);

		path[0] = StringUtil.split(contentPath, FOLDER_SEPARATOR)[0];
		path[1] = msgWrap[0];
		path[2] = msgWrap[1];
		
		return path;
	}

	public String getFilename() {
        return _filename;
    }

    public void setFilename(String filename) {
        _filename = filename;
    }

    public String getContentPath() {
        return _contentPath;
    }

    public void setContentPath(String contentPath) {
        _contentPath = contentPath;
    }
    
    public String getContentID() {
        return _contentID;
    }

    public void setContentID(String contentID) {
    	_contentID = contentID;
    }
    
    private String _contentPath;
    
    private String _contentID;

    private String _filename;
    
}