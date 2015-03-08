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

public interface _IMetaServerOperationsNC
{
    void add(String endpointStr, Song s);

    void remove(MediaInfo media);

    MediaInfo[] find(String s);

    MediaInfo[] findByArtist(String s);

    MediaInfo[] findByTitle(String s);

    MediaInfo[] listSongs();

    StreamToken setupStreaming(MediaInfo media);

    void play(StreamToken token);

    void stop(StreamToken token);
}
