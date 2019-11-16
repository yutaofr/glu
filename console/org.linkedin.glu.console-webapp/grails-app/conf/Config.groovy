/*
 * Copyright (c) 2010-2010 LinkedIn, Inc
 * Portions Copyright (c) 2011-2015 Yan Pujante
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

/////////////////////////////////////////////////////////
// Default values (can be overriden in any of the config file (see below))
/////////////////////////////////////////////////////////
// security (keystore/trustore defining security between console and agent)
console.sslEnabled = false
console.keystorePath = ""
console.keystorePassword = ""
console.keyPassword = ""
console.truststorePath = ""
console.truststorePassword = ""
console.secretkeystorePath = "/dev/null"

// if you want to restrict access to some subdirectory between agent and console
plugins.StreamFileContentPlugin.unrestrictedLocation = "/"

// set to '0' if you don't want deployments to be automatically archived
console.deploymentService.autoArchiveTimeout = "30m"

// set to true if you want to display state delta in error even if there is a delta (yellow vs red)
console.deltaService.stateDeltaOverridesDelta = true

// set to false if you want missing agents to not be skipped anymore in plan computation
console.plannerService.planner.skipMissingAgents = true

console.trackerService.zookeeperRoot = '/org/glu'

// connection timeout when the console tries to talk to the agent (rest)
console.to.agent.connectionTimeout = "30s"

/////////////////////////////////////////////////////////
// End Default values
/////////////////////////////////////////////////////////

// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

grails.config.locations = []

[ "${System.properties['user.dir']}/conf/glu-console-webapp.groovy",
  "${userHome}/.org.linkedin.glu/glu-console-webapp.groovy"].each { String filename ->
  def file = new File(filename)
  if(file.exists())
  {
    grails.config.locations << file.toURI().toURL().toString()
    println "Detected config file ${file}."
  }
}

if(System.properties["org.linkedin.glu.${appName}.config.location"]) {
  grails.config.locations << "file:" + System.properties["org.linkedin.glu.${appName}.config.location"]
}


// The ACCEPT header will not be used for content negotiation for user agents containing the following strings (defaults to the 4 major rendering engines)
grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
grails.mime.types = [ // the first one is the default format
    all:           '*/*', // 'all' maps to '*' or the first available format in withFormat
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html','application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    hal:           ['application/hal+json','application/hal+xml'],
    xml:           ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "html"

// The default scope for controllers. May be prototype, session or singleton.
// If unspecified, controllers are prototype scoped.
grails.controllers.defaultScope = 'singleton'

// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside ${}
                scriptlet = 'html' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        // filteringCodecForContentType.'text/html' = 'html'
    }
}


grails.converters.encoding = "UTF-8"
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// extra packages with domain classes
grails.external.domain.packages = [
  'org.linkedin.glu.orchestration.engine.delta',
  'org.linkedin.glu.orchestration.engine.commands'
]

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

// configure passing transaction's read-only attribute to Hibernate session, queries and criterias
// set "singleSession = false" OSIV mode in hibernate configuration after enabling
grails.hibernate.pass.readonly = false
// configure passing read-only to OSIV session by default, requires "singleSession = false" OSIV mode
grails.hibernate.osiv.readonly = false

// see ConsoleConfig for explanation
console.dev.defaults =
  [
    customCss: null,

    dashboardAgentLinksToAgent: false,

      dashboard:
      [
        [ name: "mountPoint", source: "mountPoint" ],
        [ name: "agent",      source: "agent"],
        [ name: "tags",       source: "tags",       groupBy: "uniqueVals"],
        [ name: "container",  source: "metadata.container.name"],
        [ name: "version",    source: "metadata.version"],
        [ name: "product",    source: "metadata.product"],
        [ name: "cluster",    source: "metadata.cluster"],
        [ name: "status",     source: "status" ],
        [ name: "statusInfo", source: "statusInfo", groupBy: "vals", visible: false],
        [ name: "state",      source: "state",                       visible: false]
      ],

    tags:
    [
      'a:tag1': [background: '#5a0087', color: '#ffffff'],
      'e:tag1': [background: '#00875a', color: '#ffffff'],
    ],

      model:
      [
        agent: [name: 'agent'],
        'tags.a:tag1': [name: 'a:tag1'],
        'tags.e:tag1': [name: 'e:tag1'],
        'metadata.container.name': [name: 'container'],
        'metadata.product': [name: 'product'],
        'metadata.version': [name: 'version'],
        'metadata.cluster': [name: 'cluster']
      ],

    disableModelUpdate: false,

      shortcutFilters:
      [
          [
              name: 'product',
              source: 'metadata.product',
              header: ['version']
          ]
      ],

      header:
      [
          metadata: ['drMode']
      ],

      fabrics:
      [
          'glu-dev-1': ['drMode': 'primary'],
          'glu-dev-2': ['drMode': 'secondary']
      ],

    tail: [
      size: '10k', // size to use when tailing a file by default (MemorySize)
      refreshRate: '5s' // how long between polls (Timespan)
    ],

    features:
    [
      commands: true
    ],
  ]

/**
 * Security
 * --------
 *
 * Map of URI (as found in UrlMappings) to role (USER, RELEASE, ADMIN)
 *
 * Note that the keys are using single quotes (') NOT double quotes! It is so that the $xx values
 * are not interpreted by groovy!
 */
// security
console.security.roles = [
  /**************************************
   * USER access
   */
  // dashboard
  '/dashboard': 'USER',
  '/dashboard/redelta': 'USER',
  '/dashboard/renderDelta': 'USER',
  '/dashboard/index': 'USER',
  '/dashboard/customize': 'USER',
  '/dashboard/saveAsNewCustomDashboard': 'USER',
  '/dashboard/plans': 'USER',

  // agents
  '/agents': 'USER',
  '/agents/view/$id': 'USER',
  '/agents/ps/$id': 'USER',
  '/agents/fullStackTrace/$id': 'USER',
  '/agents/tailLog/$id': 'USER',
  '/agents/fileContent/$id': 'USER',
  '/agents/plans/$id': 'USER',
  '/agents/commands/$id': 'USER',

  // plan
  '/plan/view/$id': 'USER',
  '/plan/redirectView': 'USER',
  '/plan/deployments/$id?': 'USER',
  '/plan/renderDeploymentDetails/$id?': 'USER',
  '/plan/renderDeployments': 'USER',
  '/plan/archived/$id?': 'USER',
  '/plan/create': 'USER',

  // commands
  '/commands/$id/streams': 'USER',
  '/commands/renderHistory': 'USER',
  '/commands/renderCommand/$id': 'USER',
  '/commands/list': 'USER',


  // fabric
  '/fabric/select/$id?': 'USER',

  // model
  '/model/list': 'USER',
  '/model/view/$id': 'USER',

  // user credentials
  '/user/credentials': 'USER',
  '/user/updatePassword': 'USER',

  // help
  '/help': 'USER',
  '/help/forum': 'USER',

  // /
  '/': 'USER',

  // home
  '/home': 'USER',

  /**************************************
   * RELEASE access
   */
  // agents
  '/agents/kill/$id/$pid': 'RELEASE',
  '/agents/sync/$id': 'RELEASE',
  '/agents/clearError/$id': 'RELEASE',
  '/agents/uninstallScript/$id': 'RELEASE',
  '/agents/createPlan/$id': 'RELEASE',
  '/agents/interruptAction/$id': 'RELEASE',
  '/agents/executeCommand/$id': 'RELEASE',
  '/agents/interruptCommand/$id': 'RELEASE',

  // plan
  '/plan/execute/$id': 'RELEASE',
  '/plan/filter/$id': 'RELEASE',
  '/plan/archiveAllDeployments': 'RELEASE',
  '/plan/archiveDeployment/$id': 'RELEASE',
  '/plan/resumeDeployment/$id': 'RELEASE',
  '/plan/pauseDeployment/$id': 'RELEASE',
  '/plan/abortDeployment/$id': 'RELEASE',
  '/plan/cancelStep/$id': 'RELEASE',

  // model
  '/model/choose': 'RELEASE',
  '/model/load': 'RELEASE',
  '/model/upload': 'RELEASE',
  '/model/save': 'RELEASE',
  '/model/setAsCurrent': 'RELEASE',

  // fabric
  '/fabric/refresh': 'RELEASE',

  /**************************************
   * ADMIN access
   */
  // admin
  '/admin': 'ADMIN',

  // agents
  '/agents/listVersions': 'ADMIN',
  '/agents/upgrade': 'ADMIN',
  '/agents/cleanup': 'ADMIN',
  '/agents/forceUninstallScript/$id': 'ADMIN',
  '/agent/$id/clear': 'ADMIN',

  // fabric
  '/fabric/listAgentFabrics': 'ADMIN',
  '/fabric/setAgentsFabrics': 'ADMIN',
  '/fabric/clearAgentFabric': 'ADMIN',
  '/fabric/list': 'ADMIN',
  '/fabric/show/$id': 'ADMIN',
  '/fabric/delete/$id?': 'ADMIN',
  '/fabric/edit/$id': 'ADMIN',
  '/fabric/update/$id?': 'ADMIN',
  '/fabric/create': 'ADMIN',
  '/fabric/save': 'ADMIN',

  // user
  '/user/index': 'ADMIN',
  '/user/list': 'ADMIN',
  '/user/show/$id': 'ADMIN',
  '/user/delete/$id': 'ADMIN',
  '/user/edit/$id': 'ADMIN',
  '/user/update/$id': 'ADMIN',
  '/user/create': 'ADMIN',
  '/user/save': 'ADMIN',
  '/user/resetPassword': 'ADMIN',

  // audit log
  '/auditLog/list': 'ADMIN',

  // encryption keys
  '/encryption/list': 'ADMIN',
  '/encryption/create': 'ADMIN',
  '/encryption/encrypt': 'ADMIN',
  '/encryption/ajaxSave': 'ADMIN',
  '/encryption/ajaxEncrypt': 'ADMIN',
  '/encryption/ajaxDecrypt': 'ADMIN',

  /**************************************
   * REST Api
   */
  /***
   * plan
   */
  'GET:/rest/v1/$fabric/plans': 'USER',
  'POST:/rest/v1/$fabric/plans': 'ADMIN',

  'GET:/rest/v1/$fabric/plan/$id': 'USER',

  'GET:/rest/v1/$fabric/plan/$id/executions': 'USER',

  'POST:/rest/v1/$fabric/plan/$id/execution': 'ADMIN',

  'GET:/rest/v1/$fabric/plan/$planId/execution/$id': 'USER',
  'HEAD:/rest/v1/$fabric/plan/$planId/execution/$id': 'USER',
  'DELETE:/rest/v1/$fabric/plan/$planId/execution/$id': 'ADMIN',

  /***
   * deployments
   */
  'GET:/rest/v1/$fabric/deployments/current': 'USER',
  'DELETE:/rest/v1/$fabric/deployments/current': 'ADMIN',

  'HEAD:/rest/v1/$fabric/deployment/current/$id': 'USER',
  'GET:/rest/v1/$fabric/deployment/current/$id': 'USER',
  'DELETE:/rest/v1/$fabric/deployment/current/$id': 'ADMIN',

  'HEAD:/rest/v1/$fabric/deployments/archived': 'USER',
  'GET:/rest/v1/$fabric/deployments/archived': 'USER',

  'HEAD:/rest/v1/$fabric/deployment/archived/$id': 'USER',
  'GET:/rest/v1/$fabric/deployment/archived/$id': 'USER',

  /***
   * model
   */
  'POST:/rest/v1/$fabric/model/static': 'ADMIN',
  'GET:/rest/v1/$fabric/model/static': 'USER',
  'HEAD:/rest/v1/$fabric/models/static': 'USER',
  'GET:/rest/v1/$fabric/models/static': 'USER',

  'GET:/rest/v1/$fabric/model/live': 'USER',

  /***
   * delta
   */
  'GET:/rest/v1/$fabric/model/delta': 'USER',

  /**
   * agents
   */
  'HEAD:/rest/v1/$fabric/agents': 'USER',
  'GET:/rest/v1/$fabric/agents': 'USER',

  'GET:/rest/v1/$fabric/agent/$id': 'USER',
  'DELETE:/rest/v1/$fabric/agent/$id': 'ADMIN',

  'GET:/rest/v1/$fabric/agents/versions': 'USER',
  'POST:/rest/v1/$fabric/agents/versions': 'ADMIN',

  /**
   * fabric
   */
  'GET:/rest/v1/-': 'USER',

  'GET:/rest/v1/$fabric': 'USER',
  'PUT:/rest/v1/$fabric': 'ADMIN',
  'DELETE:/rest/v1/$fabric': 'ADMIN',

  /**
   * Commands
   */
  'GET:/rest/v1/$fabric/command/$id/streams': 'USER',
  'POST:/rest/v1/$fabric/agent/$id/commands': 'RELEASE',

  'GET:/rest/v1/-/agents': 'USER',

  'PUT:/rest/v1/$fabric/agent/$id/fabric': 'ADMIN',
  'DELETE:/rest/v1/$fabric/agent/$id/fabric': 'ADMIN',

  /**
   * Audit Logs
   */
  'GET:/rest/v1/-/audit/logs': 'ADMIN',
  'HEAD:/rest/v1/-/audit/logs': 'ADMIN',

  /**
   * DEPRECATED: kept for backward compatibility only
   */
  'GET:/rest/v1/$fabric/system/model': 'USER',
  'POST:/rest/v1/$fabric/system/model': 'ADMIN',

  'GET:/rest/v1/$fabric/system/live': 'USER'
]

// set per-environment serverURL stem for creating absolute links
environments {
  production {
    // configuration parameters should be read from the console.config.location file
  }
  development {

    grails.logging.jul.usebridge = true

    // read config properties coming from the build
    grails.config.locations << "file:./lib/build.properties.groovy"

    // configuration parameters
    console.sslEnabled = true

    console.keystorePassword = 'nacEn92x8-1'
    console.keyPassword = 'nWVxpMg6Tkv'

    console.truststorePassword = 'nacEn92x8-1'

    console.bootstrap.fabrics = [
      [ name: 'glu-dev-1', zkConnectString: '127.0.0.1:2181', zkSessionTimeout: '5s', color: '#005a87' ],
      [ name: 'glu-dev-2', zkConnectString: '127.0.0.1:2181', zkSessionTimeout: '5s', color: '#5a0087' ],
    ]

    console.defaults = console.dev.defaults

    plugins.StreamFileContentPlugin.unrestrictedLocation = '/export/content/glu'
    plugins.StreamFileContentPlugin.maskFileContent = true

    grails.serverURL = "http://${InetAddress.getLocalHost().canonicalHostName}:8080/${appName}"

    // define the plugins as a Map, or a class name or an array of class names
    orchestration.engine.plugins = [
      'org.linkedin.glu.orchestration.engine.plugins.builtin.StreamFileContentPlugin'
    ]

    // storage type supported right now are 'filesystem' and 'memory'
    console.commandsService.storageType = 'filesystem'

    // log4j configuration
    log4j = {

      appenders {
        console name:'stdout', layout:pattern(conversionPattern: '%d{yyyy/MM/dd HH:mm:ss.SSS} %p [%c{1}] %m%n')
      }

      error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
             'org.codehaus.groovy.grails.web.pages', //  GSP
             'org.codehaus.groovy.grails.web.sitemesh', //  layouts
             'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
             'org.codehaus.groovy.grails.web.mapping', // URL mapping
             'org.codehaus.groovy.grails.commons', // core / classloading
             'org.codehaus.groovy.grails.plugins', // plugins
             'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
             'org.springframework',
             'org.hibernate'

      info 'grails',
           'org.linkedin',
           'org.pongasoft'

      //debug 'org.linkedin.zookeeper.tracker', 'org.linkedin.glu.agent.tracker'
      //debug 'org.apache.http'

      // debug 'org.pongasoft.grailsPlugins'

      //debug 'org.linkedin.glu.console.domain'
      //debug 'org.linkedin.glu.spring.resources.GrailsPluginLoadOrderDebugger'

//        trace 'org.hibernate.SQL', 'org.hibernate.type'
//        trace 'org.codehaus.groovy.grails.orm'
//        trace 'org.codehaus.groovy.grails.orm.hibernate'


      warn   'org.mortbay.log'
    }
  }
  test {

    // read config properties coming from the build
    grails.config.locations << "file:./lib/build.properties.groovy"

    // configuration parameters
    console.sslEnabled = true

    console.keystorePassword = 'nacEn92x8-1'
    console.keyPassword = 'nWVxpMg6Tkv'

    console.truststorePassword = 'nacEn92x8-1'

    console.defaults = console.dev.defaults

    plugins.StreamFileContentPlugin.unrestrictedLocation = '/export/content/glu'

    grails.serverURL = "http://localhost:8080/${appName}"

    // define the plugins as a Map, or a class name or an array of class names
    orchestration.engine.plugins = [
      'org.linkedin.glu.orchestration.engine.plugins.builtin.StreamFileContentPlugin'
    ]

    // storage type supported right now are 'filesystem' and 'memory'
    console.commandsService.storageType = 'filesystem'

    // log4j configuration
    log4j = {

        appenders {
            console name:'stdout', layout:pattern(conversionPattern: '%d{yyyy/MM/dd HH:mm:ss.SSS} %p [%c{1}] %m%n')
        }

        error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
             'org.codehaus.groovy.grails.web.pages', //  GSP
             'org.codehaus.groovy.grails.web.sitemesh', //  layouts
             'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
             'org.codehaus.groovy.grails.web.mapping', // URL mapping
             'org.codehaus.groovy.grails.commons', // core / classloading
             'org.codehaus.groovy.grails.plugins', // plugins
             'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
             'org.springframework',
             'org.hibernate'

        info 'grails',
             'org.linkedin'

        //debug 'org.linkedin.glu.agent.tracker', 'org.linkedin.glu.zookeeper.client'

        //trace 'org.hibernate.SQL', 'org.hibernate.type'


        warn   'org.mortbay.log'
    }
  }
}



