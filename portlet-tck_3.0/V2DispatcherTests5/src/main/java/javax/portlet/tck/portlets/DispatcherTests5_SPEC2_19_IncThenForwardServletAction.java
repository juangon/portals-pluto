/*  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package javax.portlet.tck.portlets;

import java.io.*;
import java.util.*;
import java.util.logging.*;

import javax.xml.namespace.QName;
import javax.portlet.*;
import javax.portlet.filter.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.portlet.tck.beans.*;
import javax.portlet.tck.constants.*;
import static javax.portlet.tck.beans.JSR286DispatcherTestCaseDetails.*;
import static javax.portlet.tck.constants.Constants.*;
import static javax.portlet.PortletSession.*;
import static javax.portlet.ResourceURL.*;

/**
 * This portlet implements several test cases for the JSR 362 TCK. The test case names
 * are defined in the /src/main/resources/xml-resources/additionalTCs.xml
 * file. The build process will integrate the test case names defined in the 
 * additionalTCs.xml file into the complete list of test case names for execution by the driver.
 *
 * This is the main portlet for the test cases. If the test cases call for events, this portlet
 * will initiate the events, but not process them. The processing is done in the companion 
 * portlet DispatcherTests5_SPEC2_19_IncThenForwardServletAction_event
 *
 */
public class DispatcherTests5_SPEC2_19_IncThenForwardServletAction implements Portlet, ResourceServingPortlet {


   
   private PortletConfig portletConfig = null;

   @Override
   public void init(PortletConfig config) throws PortletException {
      this.portletConfig = config;
   }

   @Override
   public void destroy() {
   }

   @Override
   public void processAction(ActionRequest portletReq, ActionResponse portletResp)
         throws PortletException, IOException {


      portletResp.setRenderParameters(portletReq.getParameterMap());
      long tid = Thread.currentThread().getId();
      portletReq.setAttribute(THREADID_ATTR, tid);

      StringWriter writer = new StringWriter();

      // Multilevel forward / include
      portletReq.setAttribute(ATTR_DISPATCH_ACTION, "forward");
      String targ = SERVLET_PREFIX + "DispatcherTests5_SPEC2_19_IncThenForwardServletAction_servlet" + EXTRA_PATH + "?" + QUERY_STRING;
      portletReq.setAttribute(ATTR_DISPATCH_TARGET, targ);

      // Now do the actual dispatch
      String target = SERVLET_INCFWD + "?" + QUERY_STRING;
      PortletRequestDispatcher rd = portletConfig.getPortletContext()
            .getRequestDispatcher(target);
      rd.include(portletReq, portletResp);
   }

   @Override
   public void serveResource(ResourceRequest portletReq, ResourceResponse portletResp)
         throws PortletException, IOException {


      long tid = Thread.currentThread().getId();
      portletReq.setAttribute(THREADID_ATTR, tid);

      PrintWriter writer = portletResp.getWriter();

   }

   @Override
   public void render(RenderRequest portletReq, RenderResponse portletResp)
         throws PortletException, IOException {


      long tid = Thread.currentThread().getId();
      portletReq.setAttribute(THREADID_ATTR, tid);

      PrintWriter writer = portletResp.getWriter();

      PortletSession ps = portletReq.getPortletSession();
      String msg = (String) ps.getAttribute(RESULT_ATTR_PREFIX + "DispatcherTests5_SPEC2_19_IncThenForwardServletAction", APPLICATION_SCOPE);
      if (msg != null) {
         writer.write("<p>" + msg + "</p><br/>\n");
         ps.removeAttribute(RESULT_ATTR_PREFIX + "DispatcherTests5_SPEC2_19_IncThenForwardServletAction", APPLICATION_SCOPE);
      }

      /* TestCase: V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_dispatch1 */
      /* Details: "In a servlet forwarded to by the target of an include,     */
      /* if the path provided to getRequestDispatcher method contains query   */
      /* strings, parameters specified in the query strings must be passed    */
      /* to the target servlet during an forward"                             */
      {
         PortletURL aurl = portletResp.createActionURL();
         aurl.setParameters(portletReq.getPrivateParameterMap());
         TestButton tb = new TestButton("V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_dispatch1", aurl);
         tb.writeTo(writer);
      }

      /* TestCase: V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_dispatch2 */
      /* Details: "In a servlet forwarded to by the target of an include,     */
      /* parameters specified in the query strings must be aggregated with    */
      /* the portlet render parameters"                                       */
      {
         PortletURL aurl = portletResp.createActionURL();
         aurl.setParameters(portletReq.getPrivateParameterMap());
         TestButton tb = new TestButton("V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_dispatch2", aurl);
         tb.writeTo(writer);
      }

      /* TestCase: V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_dispatch3 */
      /* Details: "In a servlet forwarded to by the target of an include,     */
      /* if query string parameters have the same names as render parameter   */
      /* names, the query string parameters appear in the parameter values    */
      /* array before the render parameter values"                            */
      {
         PortletURL aurl = portletResp.createActionURL();
         aurl.setParameters(portletReq.getPrivateParameterMap());
         TestButton tb = new TestButton("V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_dispatch3", aurl);
         tb.writeTo(writer);
      }

      /* TestCase: V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_invoke2 */
      /* Details: "In a servlet forwarded to by the target of an include,     */
      /* parameters to the forward method for a target servlet can be the     */
      /* request and response classes from the portlet lifecyle method        */
      /* initiating the include"                                              */
      {
         PortletURL aurl = portletResp.createActionURL();
         aurl.setParameters(portletReq.getPrivateParameterMap());
         TestButton tb = new TestButton("V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_invoke2", aurl);
         tb.writeTo(writer);
      }

      /* TestCase: V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_invoke4 */
      /* Details: "In a servlet forwarded to by the target of an include,     */
      /* the portlet container must invoke the target servlet in the same     */
      /* thread as the PortletRequestDispatcher include invocation"           */
      {
         PortletURL aurl = portletResp.createActionURL();
         aurl.setParameters(portletReq.getPrivateParameterMap());
         TestButton tb = new TestButton("V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_invoke4", aurl);
         tb.writeTo(writer);
      }

      /* TestCase: V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_invoke7 */
      /* Details: "In a servlet forwarded to by the target of an include,     */
      /* the path elements of the request object exposed to the target        */
      /* servlet must reflect the path used to obtain the                     */
      /* RequestDispatcher"                                                   */
      {
         PortletURL aurl = portletResp.createActionURL();
         aurl.setParameters(portletReq.getPrivateParameterMap());
         TestButton tb = new TestButton("V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_invoke7", aurl);
         tb.writeTo(writer);
      }

      /* TestCase: V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes1 */
      /* Details: "In a servlet forwarded to by the target of an include,     */
      /* the portlet request attribute javax.servlet.forward.request_uri      */
      /* will be set, and equals the value from                               */
      /* HTTPServletRequest.getRequestURI for the first servlet in the        */
      /* forward chain"                                                       */
      {
         PortletURL aurl = portletResp.createActionURL();
         aurl.setParameters(portletReq.getPrivateParameterMap());
         TestButton tb = new TestButton("V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes1", aurl);
         tb.writeTo(writer);
      }

      /* TestCase: V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes1a */
      /* Details: "In a servlet forwarded to by the target of an include,     */
      /* the servlet request attribute javax.servlet.forward.request_uri      */
      /* will be set, and equals the value from                               */
      /* HTTPServletRequest.getRequestURI for the first servlet in the        */
      /* forward chain"                                                       */
      {
         PortletURL aurl = portletResp.createActionURL();
         aurl.setParameters(portletReq.getPrivateParameterMap());
         TestButton tb = new TestButton("V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes1a", aurl);
         tb.writeTo(writer);
      }

      /* TestCase: V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes1b */
      /* Details: "In a servlet forwarded to by the target of an include,     */
      /* the portlet request attribute javax.servlet.include.request_uri      */
      /* will not be set"                                                     */
      {
         PortletURL aurl = portletResp.createActionURL();
         aurl.setParameters(portletReq.getPrivateParameterMap());
         TestButton tb = new TestButton("V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes1b", aurl);
         tb.writeTo(writer);
      }

      /* TestCase: V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes1c */
      /* Details: "In a servlet forwarded to by the target of an include,     */
      /* the servlet request attribute javax.servlet.include.request_uri      */
      /* will not be set"                                                     */
      {
         PortletURL aurl = portletResp.createActionURL();
         aurl.setParameters(portletReq.getPrivateParameterMap());
         TestButton tb = new TestButton("V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes1c", aurl);
         tb.writeTo(writer);
      }

      /* TestCase: V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes2 */
      /* Details: "In a servlet forwarded to by the target of an include,     */
      /* the portlet request attribute javax.servlet.forward.context_path     */
      /* will be set, and equals the value from                               */
      /* HTTPServletRequest.getContextPath for the first servlet in the       */
      /* forward chain"                                                       */
      {
         PortletURL aurl = portletResp.createActionURL();
         aurl.setParameters(portletReq.getPrivateParameterMap());
         TestButton tb = new TestButton("V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes2", aurl);
         tb.writeTo(writer);
      }

      /* TestCase: V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes2a */
      /* Details: "In a servlet forwarded to by the target of an include,     */
      /* the servlet request attribute javax.servlet.forward.context_path     */
      /* will be set, and equals the value from                               */
      /* HTTPServletRequest.getContextPath for the first servlet in the       */
      /* forward chain"                                                       */
      {
         PortletURL aurl = portletResp.createActionURL();
         aurl.setParameters(portletReq.getPrivateParameterMap());
         TestButton tb = new TestButton("V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes2a", aurl);
         tb.writeTo(writer);
      }

      /* TestCase: V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes2b */
      /* Details: "In a servlet forwarded to by the target of an include,     */
      /* the portlet request attribute javax.servlet.include.context_path     */
      /* will not be set"                                                     */
      {
         PortletURL aurl = portletResp.createActionURL();
         aurl.setParameters(portletReq.getPrivateParameterMap());
         TestButton tb = new TestButton("V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes2b", aurl);
         tb.writeTo(writer);
      }

      /* TestCase: V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes2c */
      /* Details: "In a servlet forwarded to by the target of an include,     */
      /* the servlet request attribute javax.servlet.include.context_path     */
      /* will not be set"                                                     */
      {
         PortletURL aurl = portletResp.createActionURL();
         aurl.setParameters(portletReq.getPrivateParameterMap());
         TestButton tb = new TestButton("V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes2c", aurl);
         tb.writeTo(writer);
      }

      /* TestCase: V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes3 */
      /* Details: "In a servlet forwarded to by the target of an include,     */
      /* the portlet request attribute javax.servlet.forward.servlet_path     */
      /* will be set, and equals the value from                               */
      /* HTTPServletRequest.getServletPath for the first servlet in the       */
      /* forward chain"                                                       */
      {
         PortletURL aurl = portletResp.createActionURL();
         aurl.setParameters(portletReq.getPrivateParameterMap());
         TestButton tb = new TestButton("V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes3", aurl);
         tb.writeTo(writer);
      }

      /* TestCase: V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes3a */
      /* Details: "In a servlet forwarded to by the target of an include,     */
      /* the servlet request attribute javax.servlet.forward.servlet_path     */
      /* will be set, and equals the value from                               */
      /* HTTPServletRequest.getServletPath for the first servlet in the       */
      /* forward chain"                                                       */
      {
         PortletURL aurl = portletResp.createActionURL();
         aurl.setParameters(portletReq.getPrivateParameterMap());
         TestButton tb = new TestButton("V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes3a", aurl);
         tb.writeTo(writer);
      }

      /* TestCase: V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes3b */
      /* Details: "In a servlet forwarded to by the target of an include,     */
      /* the portlet request attribute javax.servlet.include.servlet_path     */
      /* will not be set"                                                     */
      {
         PortletURL aurl = portletResp.createActionURL();
         aurl.setParameters(portletReq.getPrivateParameterMap());
         TestButton tb = new TestButton("V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes3b", aurl);
         tb.writeTo(writer);
      }

      /* TestCase: V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes3c */
      /* Details: "In a servlet forwarded to by the target of an include,     */
      /* the servlet request attribute javax.servlet.include.servlet_path     */
      /* will not be set"                                                     */
      {
         PortletURL aurl = portletResp.createActionURL();
         aurl.setParameters(portletReq.getPrivateParameterMap());
         TestButton tb = new TestButton("V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes3c", aurl);
         tb.writeTo(writer);
      }

      /* TestCase: V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes4 */
      /* Details: "In a servlet forwarded to by the target of an include,     */
      /* the portlet request attribute javax.servlet.forward.path_info will   */
      /* be set, and equals the value from HTTPServletRequest.getPathInfo     */
      /* for the first servlet in the forward chain"                          */
      {
         PortletURL aurl = portletResp.createActionURL();
         aurl.setParameters(portletReq.getPrivateParameterMap());
         TestButton tb = new TestButton("V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes4", aurl);
         tb.writeTo(writer);
      }

      /* TestCase: V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes4a */
      /* Details: "In a servlet forwarded to by the target of an include,     */
      /* the servlet request attribute javax.servlet.forward.path_info will   */
      /* be set, and equals the value from HTTPServletRequest.getPathInfo     */
      /* for the first servlet in the forward chain"                          */
      {
         PortletURL aurl = portletResp.createActionURL();
         aurl.setParameters(portletReq.getPrivateParameterMap());
         TestButton tb = new TestButton("V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes4a", aurl);
         tb.writeTo(writer);
      }

      /* TestCase: V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes4b */
      /* Details: "In a servlet forwarded to by the target of an include,     */
      /* the portlet request attribute javax.servlet.include.path_info will   */
      /* not be set"                                                          */
      {
         PortletURL aurl = portletResp.createActionURL();
         aurl.setParameters(portletReq.getPrivateParameterMap());
         TestButton tb = new TestButton("V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes4b", aurl);
         tb.writeTo(writer);
      }

      /* TestCase: V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes4c */
      /* Details: "In a servlet forwarded to by the target of an include,     */
      /* the servlet request attribute javax.servlet.include.path_info will   */
      /* not be set"                                                          */
      {
         PortletURL aurl = portletResp.createActionURL();
         aurl.setParameters(portletReq.getPrivateParameterMap());
         TestButton tb = new TestButton("V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes4c", aurl);
         tb.writeTo(writer);
      }

      /* TestCase: V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes5 */
      /* Details: "In a servlet forwarded to by the target of an include,     */
      /* the portlet request attribute javax.servlet.forward.query_string     */
      /* will be set, and equals the value from                               */
      /* HTTPServletRequest.getQueryString for the first servlet in the       */
      /* forward chain"                                                       */
      {
         PortletURL aurl = portletResp.createActionURL();
         aurl.setParameters(portletReq.getPrivateParameterMap());
         TestButton tb = new TestButton("V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes5", aurl);
         tb.writeTo(writer);
      }

      /* TestCase: V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes5a */
      /* Details: "In a servlet forwarded to by the target of an include,     */
      /* the servlet request attribute javax.servlet.forward.query_string     */
      /* will be set, and equals the value from                               */
      /* HTTPServletRequest.getQueryString for the first servlet in the       */
      /* forward chain"                                                       */
      {
         PortletURL aurl = portletResp.createActionURL();
         aurl.setParameters(portletReq.getPrivateParameterMap());
         TestButton tb = new TestButton("V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes5a", aurl);
         tb.writeTo(writer);
      }

      /* TestCase: V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes5b */
      /* Details: "In a servlet forwarded to by the target of an include,     */
      /* the portlet request attribute javax.servlet.include.query_string     */
      /* will not be set"                                                     */
      {
         PortletURL aurl = portletResp.createActionURL();
         aurl.setParameters(portletReq.getPrivateParameterMap());
         TestButton tb = new TestButton("V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes5b", aurl);
         tb.writeTo(writer);
      }

      /* TestCase: V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes5c */
      /* Details: "In a servlet forwarded to by the target of an include,     */
      /* the servlet request attribute javax.servlet.include.query_string     */
      /* will not be set"                                                     */
      {
         PortletURL aurl = portletResp.createActionURL();
         aurl.setParameters(portletReq.getPrivateParameterMap());
         TestButton tb = new TestButton("V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes5c", aurl);
         tb.writeTo(writer);
      }

      /* TestCase: V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes6 */
      /* Details: "In a servlet forwarded to by the target of an include,     */
      /* the request attribute javax.portlet.config must be set to the        */
      /* javax.portlet.PortletConfig object"                                  */
      {
         PortletURL aurl = portletResp.createActionURL();
         aurl.setParameters(portletReq.getPrivateParameterMap());
         TestButton tb = new TestButton("V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes6", aurl);
         tb.writeTo(writer);
      }

      /* TestCase: V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes7 */
      /* Details: "In a servlet forwarded to by the target of an include,     */
      /* for forwards from the processAction method, The request attribute    */
      /* javax.portlet.request must be set to the                             */
      /* javax.portlet.ActionRequest object"                                  */
      {
         PortletURL aurl = portletResp.createActionURL();
         aurl.setParameters(portletReq.getPrivateParameterMap());
         TestButton tb = new TestButton("V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes7", aurl);
         tb.writeTo(writer);
      }

      /* TestCase: V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes8 */
      /* Details: "In a servlet forwarded to by the target of an include,     */
      /* for forwards from the processAction method, The request attribute    */
      /* javax.portlet.response must be set to the                            */
      /* javax.portlet.ActionResponse object"                                 */
      {
         PortletURL aurl = portletResp.createActionURL();
         aurl.setParameters(portletReq.getPrivateParameterMap());
         TestButton tb = new TestButton("V2DispatcherTests5_SPEC2_19_IncThenForwardServletAction_attributes8", aurl);
         tb.writeTo(writer);
      }

   }

}
