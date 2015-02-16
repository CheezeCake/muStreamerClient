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

public class Song implements java.lang.Cloneable, java.io.Serializable
{
    public String artist;

    public String title;

    public String path;

    public Song()
    {
    }

    public Song(String artist, String title, String path)
    {
        this.artist = artist;
        this.title = title;
        this.path = path;
    }

    public boolean
    equals(java.lang.Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        Song _r = null;
        if(rhs instanceof Song)
        {
            _r = (Song)rhs;
        }

        if(_r != null)
        {
            if(artist != _r.artist)
            {
                if(artist == null || _r.artist == null || !artist.equals(_r.artist))
                {
                    return false;
                }
            }
            if(title != _r.title)
            {
                if(title == null || _r.title == null || !title.equals(_r.title))
                {
                    return false;
                }
            }
            if(path != _r.path)
            {
                if(path == null || _r.path == null || !path.equals(_r.path))
                {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    public int
    hashCode()
    {
        int __h = 5381;
        __h = IceInternal.HashUtil.hashAdd(__h, "::Player::Song");
        __h = IceInternal.HashUtil.hashAdd(__h, artist);
        __h = IceInternal.HashUtil.hashAdd(__h, title);
        __h = IceInternal.HashUtil.hashAdd(__h, path);
        return __h;
    }

    public java.lang.Object
    clone()
    {
        java.lang.Object o = null;
        try
        {
            o = super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return o;
    }

    public void
    __write(IceInternal.BasicStream __os)
    {
        __os.writeString(artist);
        __os.writeString(title);
        __os.writeString(path);
    }

    public void
    __read(IceInternal.BasicStream __is)
    {
        artist = __is.readString();
        title = __is.readString();
        path = __is.readString();
    }

    public static final long serialVersionUID = 4341553064857173358L;
}
