// **********************************************************************
//
// Copyright (c) 2003-2013 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.5.1
//
// <auto-generated>
//
// Generated from file `speer_serv.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package speeral;

/**
 * \brief Speeral server
 **/
public interface _ServerOperations
{
    /**
     * \brief initialize the speeral server (becomes Stopped)
     * \param configFile configuration file path (from the server location)
     * @param __current The Current object for the invocation.
     **/
    void initialize(String configFile, Ice.Current __current)
        throws SpeeralError;

    /**
     * \brief start the server (becomes Ready)
     * @param __current The Current object for the invocation.
     **/
    void start(Ice.Current __current)
        throws SpeeralError;

    /**
     * \brief stop the server (becomes Stopped)
     * @param __current The Current object for the invocation.
     **/
    void stop(Ice.Current __current)
        throws SpeeralError;

    /**
     * \brief restart (start & stop)
     * @param __current The Current object for the invocation.
     **/
    void restart(Ice.Current __current)
        throws SpeeralError;

    /**
     * \brief process a signal decoding 
     * NOTES: must be called when the server is Ready
     * \param signal the signal to process
     * \param lattice set to "true" the result is a lattice otherwise is a 1-best string
     * \return word lattice / 1-best
     * @param __current The Current object for the invocation.
     **/
    String decode(short[] signal, boolean lattice, Ice.Current __current)
        throws SpeeralError;

    /**
     * \brief change or set a specific speeral configuration option 
     * NOTES: As effect only if the server is Stopped
     * @param __current The Current object for the invocation.
     **/
    void setConfigOption(String option, String value, Ice.Current __current)
        throws SpeeralError;

    /**
     * \brief obtain the current option-value
     * \return options
     * @param __current The Current object for the invocation.
     **/
    java.util.Map<java.lang.String, java.lang.String> getCurrentConfig(Ice.Current __current)
        throws SpeeralError;

    /**
     * \brief obtain all the available option
     * \return all the available options
     * @param __current The Current object for the invocation.
     **/
    String[] getAvailableOptions(Ice.Current __current)
        throws SpeeralError;

    /**
     * \brief obtain the last ASR result (if available) 
     * \return previous 1-best 
     * @param __current The Current object for the invocation.
     **/
    String getResult(Ice.Current __current);

    /**
     * \brief obtain the last ASR lattice (if available)
     * NOTES: it works only if the lattice speeral option is setted to "yes"
     * @param __current The Current object for the invocation.
     **/
    String getLattice(Ice.Current __current);

    /**
     * \brief obtain the current server status
     * \return previous lattice
     * @param __current The Current object for the invocation.
     **/
    servStatus getStatus(Ice.Current __current);
}
