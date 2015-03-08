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

public abstract class _IMetaServerDisp extends Ice.ObjectImpl implements IMetaServer
{
    protected void
    ice_copyStateFrom(Ice.Object __obj)
        throws java.lang.CloneNotSupportedException
    {
        throw new java.lang.CloneNotSupportedException();
    }

    public static final String[] __ids =
    {
        "::Ice::Object",
        "::Player::IMetaServer"
    };

    public boolean ice_isA(String s)
    {
        return java.util.Arrays.binarySearch(__ids, s) >= 0;
    }

    public boolean ice_isA(String s, Ice.Current __current)
    {
        return java.util.Arrays.binarySearch(__ids, s) >= 0;
    }

    public String[] ice_ids()
    {
        return __ids;
    }

    public String[] ice_ids(Ice.Current __current)
    {
        return __ids;
    }

    public String ice_id()
    {
        return __ids[1];
    }

    public String ice_id(Ice.Current __current)
    {
        return __ids[1];
    }

    public static String ice_staticId()
    {
        return __ids[1];
    }

    public final void add(String endpointStr, Song s)
    {
        add(endpointStr, s, null);
    }

    public final MediaInfo[] find(String s)
    {
        return find(s, null);
    }

    public final MediaInfo[] findByArtist(String s)
    {
        return findByArtist(s, null);
    }

    public final MediaInfo[] findByTitle(String s)
    {
        return findByTitle(s, null);
    }

    public final MediaInfo[] listSongs()
    {
        return listSongs(null);
    }

    public final void play(StreamToken token)
    {
        play(token, null);
    }

    public final void remove(MediaInfo media)
    {
        remove(media, null);
    }

    public final StreamToken setupStreaming(MediaInfo media)
    {
        return setupStreaming(media, null);
    }

    public final void stop(StreamToken token)
    {
        stop(token, null);
    }

    public static Ice.DispatchStatus ___add(IMetaServer __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Normal, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        String endpointStr;
        Song s;
        endpointStr = __is.readString();
        s = new Song();
        s.__read(__is);
        __inS.endReadParams();
        __obj.add(endpointStr, s, __current);
        __inS.__writeEmptyParams();
        return Ice.DispatchStatus.DispatchOK;
    }

    public static Ice.DispatchStatus ___remove(IMetaServer __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Normal, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        MediaInfo media;
        media = new MediaInfo();
        media.__read(__is);
        __inS.endReadParams();
        __obj.remove(media, __current);
        __inS.__writeEmptyParams();
        return Ice.DispatchStatus.DispatchOK;
    }

    public static Ice.DispatchStatus ___find(IMetaServer __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Normal, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        String s;
        s = __is.readString();
        __inS.endReadParams();
        MediaInfo[] __ret = __obj.find(s, __current);
        IceInternal.BasicStream __os = __inS.__startWriteParams(Ice.FormatType.DefaultFormat);
        MediaInfoSeqHelper.write(__os, __ret);
        __inS.__endWriteParams(true);
        return Ice.DispatchStatus.DispatchOK;
    }

    public static Ice.DispatchStatus ___findByArtist(IMetaServer __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Normal, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        String s;
        s = __is.readString();
        __inS.endReadParams();
        MediaInfo[] __ret = __obj.findByArtist(s, __current);
        IceInternal.BasicStream __os = __inS.__startWriteParams(Ice.FormatType.DefaultFormat);
        MediaInfoSeqHelper.write(__os, __ret);
        __inS.__endWriteParams(true);
        return Ice.DispatchStatus.DispatchOK;
    }

    public static Ice.DispatchStatus ___findByTitle(IMetaServer __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Normal, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        String s;
        s = __is.readString();
        __inS.endReadParams();
        MediaInfo[] __ret = __obj.findByTitle(s, __current);
        IceInternal.BasicStream __os = __inS.__startWriteParams(Ice.FormatType.DefaultFormat);
        MediaInfoSeqHelper.write(__os, __ret);
        __inS.__endWriteParams(true);
        return Ice.DispatchStatus.DispatchOK;
    }

    public static Ice.DispatchStatus ___listSongs(IMetaServer __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Normal, __current.mode);
        __inS.readEmptyParams();
        MediaInfo[] __ret = __obj.listSongs(__current);
        IceInternal.BasicStream __os = __inS.__startWriteParams(Ice.FormatType.DefaultFormat);
        MediaInfoSeqHelper.write(__os, __ret);
        __inS.__endWriteParams(true);
        return Ice.DispatchStatus.DispatchOK;
    }

    public static Ice.DispatchStatus ___setupStreaming(IMetaServer __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Normal, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        MediaInfo media;
        media = new MediaInfo();
        media.__read(__is);
        __inS.endReadParams();
        StreamToken __ret = __obj.setupStreaming(media, __current);
        IceInternal.BasicStream __os = __inS.__startWriteParams(Ice.FormatType.DefaultFormat);
        __ret.__write(__os);
        __inS.__endWriteParams(true);
        return Ice.DispatchStatus.DispatchOK;
    }

    public static Ice.DispatchStatus ___play(IMetaServer __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Normal, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        StreamToken token;
        token = new StreamToken();
        token.__read(__is);
        __inS.endReadParams();
        __obj.play(token, __current);
        __inS.__writeEmptyParams();
        return Ice.DispatchStatus.DispatchOK;
    }

    public static Ice.DispatchStatus ___stop(IMetaServer __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Normal, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        StreamToken token;
        token = new StreamToken();
        token.__read(__is);
        __inS.endReadParams();
        __obj.stop(token, __current);
        __inS.__writeEmptyParams();
        return Ice.DispatchStatus.DispatchOK;
    }

    private final static String[] __all =
    {
        "add",
        "find",
        "findByArtist",
        "findByTitle",
        "ice_id",
        "ice_ids",
        "ice_isA",
        "ice_ping",
        "listSongs",
        "play",
        "remove",
        "setupStreaming",
        "stop"
    };

    public Ice.DispatchStatus __dispatch(IceInternal.Incoming in, Ice.Current __current)
    {
        int pos = java.util.Arrays.binarySearch(__all, __current.operation);
        if(pos < 0)
        {
            throw new Ice.OperationNotExistException(__current.id, __current.facet, __current.operation);
        }

        switch(pos)
        {
            case 0:
            {
                return ___add(this, in, __current);
            }
            case 1:
            {
                return ___find(this, in, __current);
            }
            case 2:
            {
                return ___findByArtist(this, in, __current);
            }
            case 3:
            {
                return ___findByTitle(this, in, __current);
            }
            case 4:
            {
                return ___ice_id(this, in, __current);
            }
            case 5:
            {
                return ___ice_ids(this, in, __current);
            }
            case 6:
            {
                return ___ice_isA(this, in, __current);
            }
            case 7:
            {
                return ___ice_ping(this, in, __current);
            }
            case 8:
            {
                return ___listSongs(this, in, __current);
            }
            case 9:
            {
                return ___play(this, in, __current);
            }
            case 10:
            {
                return ___remove(this, in, __current);
            }
            case 11:
            {
                return ___setupStreaming(this, in, __current);
            }
            case 12:
            {
                return ___stop(this, in, __current);
            }
        }

        assert(false);
        throw new Ice.OperationNotExistException(__current.id, __current.facet, __current.operation);
    }

    protected void __writeImpl(IceInternal.BasicStream __os)
    {
        __os.startWriteSlice(ice_staticId(), -1, true);
        __os.endWriteSlice();
    }

    protected void __readImpl(IceInternal.BasicStream __is)
    {
        __is.startReadSlice();
        __is.endReadSlice();
    }

    public static final long serialVersionUID = 0L;
}
