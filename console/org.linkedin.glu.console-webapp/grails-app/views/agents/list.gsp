%{--
  - Copyright (c) 2011-2015 Yan Pujante
  -
  - Licensed under the Apache License, Version 2.0 (the "License"); you may not
  - use this file except in compliance with the License. You may obtain a copy of
  - the License at
  -
  - http://www.apache.org/licenses/LICENSE-2.0
  -
  - Unless required by applicable law or agreed to in writing, software
  - distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
  - WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
  - License for the specific language governing permissions and limitations under
  - the License.
  --}%

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>Agents</title>
  <meta name="layout" content="main"/>
</head>
<body>
<ul class="nav nav-tabs">
  <li class="active"><a href="#">List</a></li>
  <cl:whenFeatureEnabled feature="commands"><li><cl:link controller="commands" action="list">All Commands</cl:link></li></cl:whenFeatureEnabled>
</ul>
<table class="table table-bordered noFullWidth alternate-row-colors">
  <thead>
  <tr>
    <th>Agent</th>
    <th>Version</th>
    <th>Hostname</th>
  </tr>
  </thead>
  <tbody>
  <g:each in="${agents}" var="entry">
    <tr>
      <td><cl:link controller="agents" action="view" id="${entry.key}">${entry.key.encodeAsHTML()}</cl:link></td>
      <td>${entry.value?.version?.encodeAsHTML() ?: '--'}</td>
      <td>${entry.value?.hostname?.encodeAsHTML() ?: '--'}</td>
    </tr>
  </g:each>
  </tbody>
</table>
</body>
</html>