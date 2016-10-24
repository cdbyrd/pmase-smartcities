package com.scda.data

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.RESTClient
import groovy.json.*

def host = 'http://numbeo.com'
def apiKey = 'ts2t085ut96sjt'
def query = [api_key:apiKey]
def path

/**
 * arg[0] - api path
 * arg[1] - query string (key=value)
 */
if (args) {
    path = args[0]

    if (args.size() > 1) {
        def elements = args[1..-1]
        elements.each {
            def values = it.tokenize('=')
            query.put(values[0], values[1])
        }
    }
}
assert path

println "Making request for ${host}${path} with query elements ${query}"
def http = new HTTPBuilder( host )
http.get( path:path, query:query ) { resp, json ->

    println "Status: ${resp.status}"
    println "Content Type: ${resp.contentType}"
    println "Payload:"

    def output = JsonOutput.toJson(json)
    println JsonOutput.prettyPrint(output)
}
