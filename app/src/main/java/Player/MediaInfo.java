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

public class MediaInfo implements java.lang.Cloneable, java.io.Serializable
{
    public String endpointStr;

    public Song media;

    public MediaInfo()
    {
    }

    public MediaInfo(String endpointStr, Song media)
    {
        this.endpointStr = endpointStr;
        this.media = media;
    }

    public boolean
    equals(java.lang.Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        MediaInfo _r = null;
        if(rhs instanceof MediaInfo)
        {
            _r = (MediaInfo)rhs;
        }

        if(_r != null)
        {
            if(endpointStr != _r.endpointStr)
            {
                if(endpointStr == null || _r.endpointStr == null || !endpointStr.equals(_r.endpointStr))
                {
                    return false;
                }
            }
            if(media != _r.media)
            {
                if(media == null || _r.media == null || !media.equals(_r.media))
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
        __h = IceInternal.HashUtil.hashAdd(__h, "::Player::MediaInfo");
        __h = IceInternal.HashUtil.hashAdd(__h, endpointStr);
        __h = IceInternal.HashUtil.hashAdd(__h, media);
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
        __os.writeString(endpointStr);
        media.__write(__os);
    }

    public void
    __read(IceInternal.BasicStream __is)
    {
        endpointStr = __is.readString();
        media = new Song();
        media.__read(__is);
    }

    public static final long serialVersionUID = -2781401933298402816L;
}
