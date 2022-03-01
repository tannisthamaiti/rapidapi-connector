package org.mule.extension.rapidapi.internal;

import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;


/**
 * This is the main class of an extension, is the entry point from which configurations, connection providers, operations
 * and sources are going to be declared.
 */
@Xml(prefix = "rapidapi")
@Extension(name = "Rapidapi-")
@Configurations(RapidapiConfiguration.class)
public class RapidapiExtension {

}
