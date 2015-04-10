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

public final class IMetaServerPrxHelper extends Ice.ObjectPrxHelperBase implements IMetaServerPrx
{
    private static final String __find_name = "find";

    public MediaInfo[] find(String s)
    {
        return find(s, null, false);
    }

    public MediaInfo[] find(String s, java.util.Map<String, String> __ctx)
    {
        return find(s, __ctx, true);
    }

    private MediaInfo[] find(String s, java.util.Map<String, String> __ctx, boolean __explicitCtx)
    {
        if(__explicitCtx && __ctx == null)
        {
            __ctx = _emptyContext;
        }
        final Ice.Instrumentation.InvocationObserver __observer = IceInternal.ObserverHelper.get(this, "find", __ctx);
        int __cnt = 0;
        try
        {
            while(true)
            {
                Ice._ObjectDel __delBase = null;
                try
                {
                    __checkTwowayOnly("find");
                    __delBase = __getDelegate(false);
                    _IMetaServerDel __del = (_IMetaServerDel)__delBase;
                    return __del.find(s, __ctx, __observer);
                }
                catch(IceInternal.LocalExceptionWrapper __ex)
                {
                    __handleExceptionWrapper(__delBase, __ex, __observer);
                }
                catch(Ice.LocalException __ex)
                {
                    __cnt = __handleException(__delBase, __ex, null, __cnt, __observer);
                }
            }
        }
        finally
        {
            if(__observer != null)
            {
                __observer.detach();
            }
        }
    }

    public Ice.AsyncResult begin_find(String s)
    {
        return begin_find(s, null, false, null);
    }

    public Ice.AsyncResult begin_find(String s, java.util.Map<String, String> __ctx)
    {
        return begin_find(s, __ctx, true, null);
    }

    public Ice.AsyncResult begin_find(String s, Ice.Callback __cb)
    {
        return begin_find(s, null, false, __cb);
    }

    public Ice.AsyncResult begin_find(String s, java.util.Map<String, String> __ctx, Ice.Callback __cb)
    {
        return begin_find(s, __ctx, true, __cb);
    }

    public Ice.AsyncResult begin_find(String s, Callback_IMetaServer_find __cb)
    {
        return begin_find(s, null, false, __cb);
    }

    public Ice.AsyncResult begin_find(String s, java.util.Map<String, String> __ctx, Callback_IMetaServer_find __cb)
    {
        return begin_find(s, __ctx, true, __cb);
    }

    private Ice.AsyncResult begin_find(String s, java.util.Map<String, String> __ctx, boolean __explicitCtx, IceInternal.CallbackBase __cb)
    {
        __checkAsyncTwowayOnly(__find_name);
        IceInternal.OutgoingAsync __result = new IceInternal.OutgoingAsync(this, __find_name, __cb);
        try
        {
            __result.__prepare(__find_name, Ice.OperationMode.Normal, __ctx, __explicitCtx);
            IceInternal.BasicStream __os = __result.__startWriteParams(Ice.FormatType.DefaultFormat);
            __os.writeString(s);
            __result.__endWriteParams();
            __result.__send(true);
        }
        catch(Ice.LocalException __ex)
        {
            __result.__exceptionAsync(__ex);
        }
        return __result;
    }

    public MediaInfo[] end_find(Ice.AsyncResult __result)
    {
        Ice.AsyncResult.__check(__result, this, __find_name);
        boolean __ok = __result.__wait();
        try
        {
            if(!__ok)
            {
                try
                {
                    __result.__throwUserException();
                }
                catch(Ice.UserException __ex)
                {
                    throw new Ice.UnknownUserException(__ex.ice_name(), __ex);
                }
            }
            IceInternal.BasicStream __is = __result.__startReadParams();
            MediaInfo[] __ret;
            __ret = MediaInfoSeqHelper.read(__is);
            __result.__endReadParams();
            return __ret;
        }
        catch(Ice.LocalException ex)
        {
            Ice.Instrumentation.InvocationObserver __obsv = __result.__getObserver();
            if(__obsv != null)
            {
                __obsv.failed(ex.ice_name());
            }
            throw ex;
        }
    }

    private static final String __findByArtist_name = "findByArtist";

    public MediaInfo[] findByArtist(String s)
    {
        return findByArtist(s, null, false);
    }

    public MediaInfo[] findByArtist(String s, java.util.Map<String, String> __ctx)
    {
        return findByArtist(s, __ctx, true);
    }

    private MediaInfo[] findByArtist(String s, java.util.Map<String, String> __ctx, boolean __explicitCtx)
    {
        if(__explicitCtx && __ctx == null)
        {
            __ctx = _emptyContext;
        }
        final Ice.Instrumentation.InvocationObserver __observer = IceInternal.ObserverHelper.get(this, "findByArtist", __ctx);
        int __cnt = 0;
        try
        {
            while(true)
            {
                Ice._ObjectDel __delBase = null;
                try
                {
                    __checkTwowayOnly("findByArtist");
                    __delBase = __getDelegate(false);
                    _IMetaServerDel __del = (_IMetaServerDel)__delBase;
                    return __del.findByArtist(s, __ctx, __observer);
                }
                catch(IceInternal.LocalExceptionWrapper __ex)
                {
                    __handleExceptionWrapper(__delBase, __ex, __observer);
                }
                catch(Ice.LocalException __ex)
                {
                    __cnt = __handleException(__delBase, __ex, null, __cnt, __observer);
                }
            }
        }
        finally
        {
            if(__observer != null)
            {
                __observer.detach();
            }
        }
    }

    public Ice.AsyncResult begin_findByArtist(String s)
    {
        return begin_findByArtist(s, null, false, null);
    }

    public Ice.AsyncResult begin_findByArtist(String s, java.util.Map<String, String> __ctx)
    {
        return begin_findByArtist(s, __ctx, true, null);
    }

    public Ice.AsyncResult begin_findByArtist(String s, Ice.Callback __cb)
    {
        return begin_findByArtist(s, null, false, __cb);
    }

    public Ice.AsyncResult begin_findByArtist(String s, java.util.Map<String, String> __ctx, Ice.Callback __cb)
    {
        return begin_findByArtist(s, __ctx, true, __cb);
    }

    public Ice.AsyncResult begin_findByArtist(String s, Callback_IMetaServer_findByArtist __cb)
    {
        return begin_findByArtist(s, null, false, __cb);
    }

    public Ice.AsyncResult begin_findByArtist(String s, java.util.Map<String, String> __ctx, Callback_IMetaServer_findByArtist __cb)
    {
        return begin_findByArtist(s, __ctx, true, __cb);
    }

    private Ice.AsyncResult begin_findByArtist(String s, java.util.Map<String, String> __ctx, boolean __explicitCtx, IceInternal.CallbackBase __cb)
    {
        __checkAsyncTwowayOnly(__findByArtist_name);
        IceInternal.OutgoingAsync __result = new IceInternal.OutgoingAsync(this, __findByArtist_name, __cb);
        try
        {
            __result.__prepare(__findByArtist_name, Ice.OperationMode.Normal, __ctx, __explicitCtx);
            IceInternal.BasicStream __os = __result.__startWriteParams(Ice.FormatType.DefaultFormat);
            __os.writeString(s);
            __result.__endWriteParams();
            __result.__send(true);
        }
        catch(Ice.LocalException __ex)
        {
            __result.__exceptionAsync(__ex);
        }
        return __result;
    }

    public MediaInfo[] end_findByArtist(Ice.AsyncResult __result)
    {
        Ice.AsyncResult.__check(__result, this, __findByArtist_name);
        boolean __ok = __result.__wait();
        try
        {
            if(!__ok)
            {
                try
                {
                    __result.__throwUserException();
                }
                catch(Ice.UserException __ex)
                {
                    throw new Ice.UnknownUserException(__ex.ice_name(), __ex);
                }
            }
            IceInternal.BasicStream __is = __result.__startReadParams();
            MediaInfo[] __ret;
            __ret = MediaInfoSeqHelper.read(__is);
            __result.__endReadParams();
            return __ret;
        }
        catch(Ice.LocalException ex)
        {
            Ice.Instrumentation.InvocationObserver __obsv = __result.__getObserver();
            if(__obsv != null)
            {
                __obsv.failed(ex.ice_name());
            }
            throw ex;
        }
    }

    private static final String __findByTitle_name = "findByTitle";

    public MediaInfo[] findByTitle(String s)
    {
        return findByTitle(s, null, false);
    }

    public MediaInfo[] findByTitle(String s, java.util.Map<String, String> __ctx)
    {
        return findByTitle(s, __ctx, true);
    }

    private MediaInfo[] findByTitle(String s, java.util.Map<String, String> __ctx, boolean __explicitCtx)
    {
        if(__explicitCtx && __ctx == null)
        {
            __ctx = _emptyContext;
        }
        final Ice.Instrumentation.InvocationObserver __observer = IceInternal.ObserverHelper.get(this, "findByTitle", __ctx);
        int __cnt = 0;
        try
        {
            while(true)
            {
                Ice._ObjectDel __delBase = null;
                try
                {
                    __checkTwowayOnly("findByTitle");
                    __delBase = __getDelegate(false);
                    _IMetaServerDel __del = (_IMetaServerDel)__delBase;
                    return __del.findByTitle(s, __ctx, __observer);
                }
                catch(IceInternal.LocalExceptionWrapper __ex)
                {
                    __handleExceptionWrapper(__delBase, __ex, __observer);
                }
                catch(Ice.LocalException __ex)
                {
                    __cnt = __handleException(__delBase, __ex, null, __cnt, __observer);
                }
            }
        }
        finally
        {
            if(__observer != null)
            {
                __observer.detach();
            }
        }
    }

    public Ice.AsyncResult begin_findByTitle(String s)
    {
        return begin_findByTitle(s, null, false, null);
    }

    public Ice.AsyncResult begin_findByTitle(String s, java.util.Map<String, String> __ctx)
    {
        return begin_findByTitle(s, __ctx, true, null);
    }

    public Ice.AsyncResult begin_findByTitle(String s, Ice.Callback __cb)
    {
        return begin_findByTitle(s, null, false, __cb);
    }

    public Ice.AsyncResult begin_findByTitle(String s, java.util.Map<String, String> __ctx, Ice.Callback __cb)
    {
        return begin_findByTitle(s, __ctx, true, __cb);
    }

    public Ice.AsyncResult begin_findByTitle(String s, Callback_IMetaServer_findByTitle __cb)
    {
        return begin_findByTitle(s, null, false, __cb);
    }

    public Ice.AsyncResult begin_findByTitle(String s, java.util.Map<String, String> __ctx, Callback_IMetaServer_findByTitle __cb)
    {
        return begin_findByTitle(s, __ctx, true, __cb);
    }

    private Ice.AsyncResult begin_findByTitle(String s, java.util.Map<String, String> __ctx, boolean __explicitCtx, IceInternal.CallbackBase __cb)
    {
        __checkAsyncTwowayOnly(__findByTitle_name);
        IceInternal.OutgoingAsync __result = new IceInternal.OutgoingAsync(this, __findByTitle_name, __cb);
        try
        {
            __result.__prepare(__findByTitle_name, Ice.OperationMode.Normal, __ctx, __explicitCtx);
            IceInternal.BasicStream __os = __result.__startWriteParams(Ice.FormatType.DefaultFormat);
            __os.writeString(s);
            __result.__endWriteParams();
            __result.__send(true);
        }
        catch(Ice.LocalException __ex)
        {
            __result.__exceptionAsync(__ex);
        }
        return __result;
    }

    public MediaInfo[] end_findByTitle(Ice.AsyncResult __result)
    {
        Ice.AsyncResult.__check(__result, this, __findByTitle_name);
        boolean __ok = __result.__wait();
        try
        {
            if(!__ok)
            {
                try
                {
                    __result.__throwUserException();
                }
                catch(Ice.UserException __ex)
                {
                    throw new Ice.UnknownUserException(__ex.ice_name(), __ex);
                }
            }
            IceInternal.BasicStream __is = __result.__startReadParams();
            MediaInfo[] __ret;
            __ret = MediaInfoSeqHelper.read(__is);
            __result.__endReadParams();
            return __ret;
        }
        catch(Ice.LocalException ex)
        {
            Ice.Instrumentation.InvocationObserver __obsv = __result.__getObserver();
            if(__obsv != null)
            {
                __obsv.failed(ex.ice_name());
            }
            throw ex;
        }
    }

    private static final String __listMusicServers_name = "listMusicServers";

    public MusicServerInfo[] listMusicServers()
    {
        return listMusicServers(null, false);
    }

    public MusicServerInfo[] listMusicServers(java.util.Map<String, String> __ctx)
    {
        return listMusicServers(__ctx, true);
    }

    private MusicServerInfo[] listMusicServers(java.util.Map<String, String> __ctx, boolean __explicitCtx)
    {
        if(__explicitCtx && __ctx == null)
        {
            __ctx = _emptyContext;
        }
        final Ice.Instrumentation.InvocationObserver __observer = IceInternal.ObserverHelper.get(this, "listMusicServers", __ctx);
        int __cnt = 0;
        try
        {
            while(true)
            {
                Ice._ObjectDel __delBase = null;
                try
                {
                    __checkTwowayOnly("listMusicServers");
                    __delBase = __getDelegate(false);
                    _IMetaServerDel __del = (_IMetaServerDel)__delBase;
                    return __del.listMusicServers(__ctx, __observer);
                }
                catch(IceInternal.LocalExceptionWrapper __ex)
                {
                    __handleExceptionWrapper(__delBase, __ex, __observer);
                }
                catch(Ice.LocalException __ex)
                {
                    __cnt = __handleException(__delBase, __ex, null, __cnt, __observer);
                }
            }
        }
        finally
        {
            if(__observer != null)
            {
                __observer.detach();
            }
        }
    }

    public Ice.AsyncResult begin_listMusicServers()
    {
        return begin_listMusicServers(null, false, null);
    }

    public Ice.AsyncResult begin_listMusicServers(java.util.Map<String, String> __ctx)
    {
        return begin_listMusicServers(__ctx, true, null);
    }

    public Ice.AsyncResult begin_listMusicServers(Ice.Callback __cb)
    {
        return begin_listMusicServers(null, false, __cb);
    }

    public Ice.AsyncResult begin_listMusicServers(java.util.Map<String, String> __ctx, Ice.Callback __cb)
    {
        return begin_listMusicServers(__ctx, true, __cb);
    }

    public Ice.AsyncResult begin_listMusicServers(Callback_IMetaServer_listMusicServers __cb)
    {
        return begin_listMusicServers(null, false, __cb);
    }

    public Ice.AsyncResult begin_listMusicServers(java.util.Map<String, String> __ctx, Callback_IMetaServer_listMusicServers __cb)
    {
        return begin_listMusicServers(__ctx, true, __cb);
    }

    private Ice.AsyncResult begin_listMusicServers(java.util.Map<String, String> __ctx, boolean __explicitCtx, IceInternal.CallbackBase __cb)
    {
        __checkAsyncTwowayOnly(__listMusicServers_name);
        IceInternal.OutgoingAsync __result = new IceInternal.OutgoingAsync(this, __listMusicServers_name, __cb);
        try
        {
            __result.__prepare(__listMusicServers_name, Ice.OperationMode.Normal, __ctx, __explicitCtx);
            __result.__writeEmptyParams();
            __result.__send(true);
        }
        catch(Ice.LocalException __ex)
        {
            __result.__exceptionAsync(__ex);
        }
        return __result;
    }

    public MusicServerInfo[] end_listMusicServers(Ice.AsyncResult __result)
    {
        Ice.AsyncResult.__check(__result, this, __listMusicServers_name);
        boolean __ok = __result.__wait();
        try
        {
            if(!__ok)
            {
                try
                {
                    __result.__throwUserException();
                }
                catch(Ice.UserException __ex)
                {
                    throw new Ice.UnknownUserException(__ex.ice_name(), __ex);
                }
            }
            IceInternal.BasicStream __is = __result.__startReadParams();
            MusicServerInfo[] __ret;
            __ret = MusicServerInfoSeqHelper.read(__is);
            __result.__endReadParams();
            return __ret;
        }
        catch(Ice.LocalException ex)
        {
            Ice.Instrumentation.InvocationObserver __obsv = __result.__getObserver();
            if(__obsv != null)
            {
                __obsv.failed(ex.ice_name());
            }
            throw ex;
        }
    }

    private static final String __listSongs_name = "listSongs";

    public MediaInfo[] listSongs()
    {
        return listSongs(null, false);
    }

    public MediaInfo[] listSongs(java.util.Map<String, String> __ctx)
    {
        return listSongs(__ctx, true);
    }

    private MediaInfo[] listSongs(java.util.Map<String, String> __ctx, boolean __explicitCtx)
    {
        if(__explicitCtx && __ctx == null)
        {
            __ctx = _emptyContext;
        }
        final Ice.Instrumentation.InvocationObserver __observer = IceInternal.ObserverHelper.get(this, "listSongs", __ctx);
        int __cnt = 0;
        try
        {
            while(true)
            {
                Ice._ObjectDel __delBase = null;
                try
                {
                    __checkTwowayOnly("listSongs");
                    __delBase = __getDelegate(false);
                    _IMetaServerDel __del = (_IMetaServerDel)__delBase;
                    return __del.listSongs(__ctx, __observer);
                }
                catch(IceInternal.LocalExceptionWrapper __ex)
                {
                    __handleExceptionWrapper(__delBase, __ex, __observer);
                }
                catch(Ice.LocalException __ex)
                {
                    __cnt = __handleException(__delBase, __ex, null, __cnt, __observer);
                }
            }
        }
        finally
        {
            if(__observer != null)
            {
                __observer.detach();
            }
        }
    }

    public Ice.AsyncResult begin_listSongs()
    {
        return begin_listSongs(null, false, null);
    }

    public Ice.AsyncResult begin_listSongs(java.util.Map<String, String> __ctx)
    {
        return begin_listSongs(__ctx, true, null);
    }

    public Ice.AsyncResult begin_listSongs(Ice.Callback __cb)
    {
        return begin_listSongs(null, false, __cb);
    }

    public Ice.AsyncResult begin_listSongs(java.util.Map<String, String> __ctx, Ice.Callback __cb)
    {
        return begin_listSongs(__ctx, true, __cb);
    }

    public Ice.AsyncResult begin_listSongs(Callback_IMetaServer_listSongs __cb)
    {
        return begin_listSongs(null, false, __cb);
    }

    public Ice.AsyncResult begin_listSongs(java.util.Map<String, String> __ctx, Callback_IMetaServer_listSongs __cb)
    {
        return begin_listSongs(__ctx, true, __cb);
    }

    private Ice.AsyncResult begin_listSongs(java.util.Map<String, String> __ctx, boolean __explicitCtx, IceInternal.CallbackBase __cb)
    {
        __checkAsyncTwowayOnly(__listSongs_name);
        IceInternal.OutgoingAsync __result = new IceInternal.OutgoingAsync(this, __listSongs_name, __cb);
        try
        {
            __result.__prepare(__listSongs_name, Ice.OperationMode.Normal, __ctx, __explicitCtx);
            __result.__writeEmptyParams();
            __result.__send(true);
        }
        catch(Ice.LocalException __ex)
        {
            __result.__exceptionAsync(__ex);
        }
        return __result;
    }

    public MediaInfo[] end_listSongs(Ice.AsyncResult __result)
    {
        Ice.AsyncResult.__check(__result, this, __listSongs_name);
        boolean __ok = __result.__wait();
        try
        {
            if(!__ok)
            {
                try
                {
                    __result.__throwUserException();
                }
                catch(Ice.UserException __ex)
                {
                    throw new Ice.UnknownUserException(__ex.ice_name(), __ex);
                }
            }
            IceInternal.BasicStream __is = __result.__startReadParams();
            MediaInfo[] __ret;
            __ret = MediaInfoSeqHelper.read(__is);
            __result.__endReadParams();
            return __ret;
        }
        catch(Ice.LocalException ex)
        {
            Ice.Instrumentation.InvocationObserver __obsv = __result.__getObserver();
            if(__obsv != null)
            {
                __obsv.failed(ex.ice_name());
            }
            throw ex;
        }
    }

    private static final String __setupStreaming_name = "setupStreaming";

    public StreamToken setupStreaming(MediaInfo media)
    {
        return setupStreaming(media, null, false);
    }

    public StreamToken setupStreaming(MediaInfo media, java.util.Map<String, String> __ctx)
    {
        return setupStreaming(media, __ctx, true);
    }

    private StreamToken setupStreaming(MediaInfo media, java.util.Map<String, String> __ctx, boolean __explicitCtx)
    {
        if(__explicitCtx && __ctx == null)
        {
            __ctx = _emptyContext;
        }
        final Ice.Instrumentation.InvocationObserver __observer = IceInternal.ObserverHelper.get(this, "setupStreaming", __ctx);
        int __cnt = 0;
        try
        {
            while(true)
            {
                Ice._ObjectDel __delBase = null;
                try
                {
                    __checkTwowayOnly("setupStreaming");
                    __delBase = __getDelegate(false);
                    _IMetaServerDel __del = (_IMetaServerDel)__delBase;
                    return __del.setupStreaming(media, __ctx, __observer);
                }
                catch(IceInternal.LocalExceptionWrapper __ex)
                {
                    __handleExceptionWrapper(__delBase, __ex, __observer);
                }
                catch(Ice.LocalException __ex)
                {
                    __cnt = __handleException(__delBase, __ex, null, __cnt, __observer);
                }
            }
        }
        finally
        {
            if(__observer != null)
            {
                __observer.detach();
            }
        }
    }

    public Ice.AsyncResult begin_setupStreaming(MediaInfo media)
    {
        return begin_setupStreaming(media, null, false, null);
    }

    public Ice.AsyncResult begin_setupStreaming(MediaInfo media, java.util.Map<String, String> __ctx)
    {
        return begin_setupStreaming(media, __ctx, true, null);
    }

    public Ice.AsyncResult begin_setupStreaming(MediaInfo media, Ice.Callback __cb)
    {
        return begin_setupStreaming(media, null, false, __cb);
    }

    public Ice.AsyncResult begin_setupStreaming(MediaInfo media, java.util.Map<String, String> __ctx, Ice.Callback __cb)
    {
        return begin_setupStreaming(media, __ctx, true, __cb);
    }

    public Ice.AsyncResult begin_setupStreaming(MediaInfo media, Callback_IMetaServer_setupStreaming __cb)
    {
        return begin_setupStreaming(media, null, false, __cb);
    }

    public Ice.AsyncResult begin_setupStreaming(MediaInfo media, java.util.Map<String, String> __ctx, Callback_IMetaServer_setupStreaming __cb)
    {
        return begin_setupStreaming(media, __ctx, true, __cb);
    }

    private Ice.AsyncResult begin_setupStreaming(MediaInfo media, java.util.Map<String, String> __ctx, boolean __explicitCtx, IceInternal.CallbackBase __cb)
    {
        __checkAsyncTwowayOnly(__setupStreaming_name);
        IceInternal.OutgoingAsync __result = new IceInternal.OutgoingAsync(this, __setupStreaming_name, __cb);
        try
        {
            __result.__prepare(__setupStreaming_name, Ice.OperationMode.Normal, __ctx, __explicitCtx);
            IceInternal.BasicStream __os = __result.__startWriteParams(Ice.FormatType.DefaultFormat);
            media.__write(__os);
            __result.__endWriteParams();
            __result.__send(true);
        }
        catch(Ice.LocalException __ex)
        {
            __result.__exceptionAsync(__ex);
        }
        return __result;
    }

    public StreamToken end_setupStreaming(Ice.AsyncResult __result)
    {
        Ice.AsyncResult.__check(__result, this, __setupStreaming_name);
        boolean __ok = __result.__wait();
        try
        {
            if(!__ok)
            {
                try
                {
                    __result.__throwUserException();
                }
                catch(Ice.UserException __ex)
                {
                    throw new Ice.UnknownUserException(__ex.ice_name(), __ex);
                }
            }
            IceInternal.BasicStream __is = __result.__startReadParams();
            StreamToken __ret;
            __ret = new StreamToken();
            __ret.__read(__is);
            __result.__endReadParams();
            return __ret;
        }
        catch(Ice.LocalException ex)
        {
            Ice.Instrumentation.InvocationObserver __obsv = __result.__getObserver();
            if(__obsv != null)
            {
                __obsv.failed(ex.ice_name());
            }
            throw ex;
        }
    }

    public static IMetaServerPrx checkedCast(Ice.ObjectPrx __obj)
    {
        IMetaServerPrx __d = null;
        if(__obj != null)
        {
            if(__obj instanceof IMetaServerPrx)
            {
                __d = (IMetaServerPrx)__obj;
            }
            else
            {
                if(__obj.ice_isA(ice_staticId()))
                {
                    IMetaServerPrxHelper __h = new IMetaServerPrxHelper();
                    __h.__copyFrom(__obj);
                    __d = __h;
                }
            }
        }
        return __d;
    }

    public static IMetaServerPrx checkedCast(Ice.ObjectPrx __obj, java.util.Map<String, String> __ctx)
    {
        IMetaServerPrx __d = null;
        if(__obj != null)
        {
            if(__obj instanceof IMetaServerPrx)
            {
                __d = (IMetaServerPrx)__obj;
            }
            else
            {
                if(__obj.ice_isA(ice_staticId(), __ctx))
                {
                    IMetaServerPrxHelper __h = new IMetaServerPrxHelper();
                    __h.__copyFrom(__obj);
                    __d = __h;
                }
            }
        }
        return __d;
    }

    public static IMetaServerPrx checkedCast(Ice.ObjectPrx __obj, String __facet)
    {
        IMetaServerPrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            try
            {
                if(__bb.ice_isA(ice_staticId()))
                {
                    IMetaServerPrxHelper __h = new IMetaServerPrxHelper();
                    __h.__copyFrom(__bb);
                    __d = __h;
                }
            }
            catch(Ice.FacetNotExistException ex)
            {
            }
        }
        return __d;
    }

    public static IMetaServerPrx checkedCast(Ice.ObjectPrx __obj, String __facet, java.util.Map<String, String> __ctx)
    {
        IMetaServerPrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            try
            {
                if(__bb.ice_isA(ice_staticId(), __ctx))
                {
                    IMetaServerPrxHelper __h = new IMetaServerPrxHelper();
                    __h.__copyFrom(__bb);
                    __d = __h;
                }
            }
            catch(Ice.FacetNotExistException ex)
            {
            }
        }
        return __d;
    }

    public static IMetaServerPrx uncheckedCast(Ice.ObjectPrx __obj)
    {
        IMetaServerPrx __d = null;
        if(__obj != null)
        {
            if(__obj instanceof IMetaServerPrx)
            {
                __d = (IMetaServerPrx)__obj;
            }
            else
            {
                IMetaServerPrxHelper __h = new IMetaServerPrxHelper();
                __h.__copyFrom(__obj);
                __d = __h;
            }
        }
        return __d;
    }

    public static IMetaServerPrx uncheckedCast(Ice.ObjectPrx __obj, String __facet)
    {
        IMetaServerPrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            IMetaServerPrxHelper __h = new IMetaServerPrxHelper();
            __h.__copyFrom(__bb);
            __d = __h;
        }
        return __d;
    }

    public static final String[] __ids =
    {
        "::Ice::Object",
        "::Player::IMetaServer"
    };

    public static String ice_staticId()
    {
        return __ids[1];
    }

    protected Ice._ObjectDelM __createDelegateM()
    {
        return new _IMetaServerDelM();
    }

    protected Ice._ObjectDelD __createDelegateD()
    {
        return new _IMetaServerDelD();
    }

    public static void __write(IceInternal.BasicStream __os, IMetaServerPrx v)
    {
        __os.writeProxy(v);
    }

    public static IMetaServerPrx __read(IceInternal.BasicStream __is)
    {
        Ice.ObjectPrx proxy = __is.readProxy();
        if(proxy != null)
        {
            IMetaServerPrxHelper result = new IMetaServerPrxHelper();
            result.__copyFrom(proxy);
            return result;
        }
        return null;
    }

    public static final long serialVersionUID = 0L;
}
