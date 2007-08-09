/*
 * Copyright 2003,2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.pluto.internal.impl;

import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import org.apache.pluto.PortletContainer;
import org.apache.pluto.internal.InternalPortletWindow;

/**
 * Refactoring where functionality was pushed into JSR-286 <code>BaseURL</code>.
 *
 * @since 2.0
 */
public class PortletURLImpl extends BaseURLImpl implements PortletURL {

    public PortletURLImpl(PortletContainer container,
                          InternalPortletWindow internalPortletWindow,
                          javax.servlet.http.HttpServletRequest servletRequest,
                          javax.servlet.http.HttpServletResponse servletResponse,
                          boolean isAction) {
    	super(container,internalPortletWindow,servletRequest,
    			servletResponse,isAction,false);
    }

    // javax.portlet.PortletURL -------------------------------------------------------------------
    public void setWindowState(WindowState windowState)
        throws WindowStateException {
        if (windowState != null && isWindowStateAllowed(windowState)) {
            state = windowState;
            return;
        }

        throw new WindowStateException(
            "unsupported Window State used: " + windowState, windowState);
    }

    public void setPortletMode(PortletMode portletMode)
        throws PortletModeException {
        // Test and throw exception if not allowed.
        isPortletModeAllowed(portletMode);
        mode = portletMode;
    }

}