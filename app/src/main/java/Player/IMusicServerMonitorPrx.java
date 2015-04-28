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

public interface IMusicServerMonitorPrx extends Ice.ObjectPrx
{
    public void newMusicServer(String hostname, String listeningPort, String streamingPort);

    public void newMusicServer(String hostname, String listeningPort, String streamingPort, java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_newMusicServer(String hostname, String listeningPort, String streamingPort);

    public Ice.AsyncResult begin_newMusicServer(String hostname, String listeningPort, String streamingPort, java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_newMusicServer(String hostname, String listeningPort, String streamingPort, Ice.Callback __cb);

    public Ice.AsyncResult begin_newMusicServer(String hostname, String listeningPort, String streamingPort, java.util.Map<String, String> __ctx, Ice.Callback __cb);

    public Ice.AsyncResult begin_newMusicServer(String hostname, String listeningPort, String streamingPort, Callback_IMusicServerMonitor_newMusicServer __cb);

    public Ice.AsyncResult begin_newMusicServer(String hostname, String listeningPort, String streamingPort, java.util.Map<String, String> __ctx, Callback_IMusicServerMonitor_newMusicServer __cb);

    public void end_newMusicServer(Ice.AsyncResult __result);

    public void musicServerDown(String hostname, String listeningPort, String streamingPort);

    public void musicServerDown(String hostname, String listeningPort, String streamingPort, java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_musicServerDown(String hostname, String listeningPort, String streamingPort);

    public Ice.AsyncResult begin_musicServerDown(String hostname, String listeningPort, String streamingPort, java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_musicServerDown(String hostname, String listeningPort, String streamingPort, Ice.Callback __cb);

    public Ice.AsyncResult begin_musicServerDown(String hostname, String listeningPort, String streamingPort, java.util.Map<String, String> __ctx, Ice.Callback __cb);

    public Ice.AsyncResult begin_musicServerDown(String hostname, String listeningPort, String streamingPort, Callback_IMusicServerMonitor_musicServerDown __cb);

    public Ice.AsyncResult begin_musicServerDown(String hostname, String listeningPort, String streamingPort, java.util.Map<String, String> __ctx, Callback_IMusicServerMonitor_musicServerDown __cb);

    public void end_musicServerDown(Ice.AsyncResult __result);
}