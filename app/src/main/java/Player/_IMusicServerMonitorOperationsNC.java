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
// Generated from file `server.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Player;

public interface _IMusicServerMonitorOperationsNC
{
    void newMusicServer(String hostname, String listeningPort, String streamingPort);

    void musicServerDown(String hostname, String listeningPort, String streamingPort);
}