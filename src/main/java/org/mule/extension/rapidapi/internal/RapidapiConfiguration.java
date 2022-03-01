package org.mule.extension.rapidapi.internal;


import org.mule.runtime.extension.api.annotation.Configuration;

import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;


/**
 * This class represents an extension configuration, values set in this class are commonly used across multiple
 * operations since they represent something core from the extension.
 */
@Configuration(name = "config")

@Operations(RapidapiOperations.class)
@ConnectionProviders(RapidapiConnectionProvider.class)
public class RapidapiConfiguration {

  
 
}
