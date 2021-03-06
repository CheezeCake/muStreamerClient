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

public final class ISongMonitorPrxHelper extends Ice.ObjectPrxHelperBase implements ISongMonitorPrx
{
    private static final String __newSong_name = "newSong";

    public void newSong(Song s)
    {
        newSong(s, null, false);
    }

    public void newSong(Song s, java.util.Map<String, String> __ctx)
    {
        newSong(s, __ctx, true);
    }

    private void newSong(Song s, java.util.Map<String, String> __ctx, boolean __explicitCtx)
    {
        if(__explicitCtx && __ctx == null)
        {
            __ctx = _emptyContext;
        }
        final Ice.Instrumentation.InvocationObserver __observer = IceInternal.ObserverHelper.get(this, "newSong", __ctx);
        int __cnt = 0;
        try
        {
            while(true)
            {
                Ice._ObjectDel __delBase = null;
                try
                {
                    __delBase = __getDelegate(false);
                    _ISongMonitorDel __del = (_ISongMonitorDel)__delBase;
                    __del.newSong(s, __ctx, __observer);
                    return;
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

    public Ice.AsyncResult begin_newSong(Song s)
    {
        return begin_newSong(s, null, false, null);
    }

    public Ice.AsyncResult begin_newSong(Song s, java.util.Map<String, String> __ctx)
    {
        return begin_newSong(s, __ctx, true, null);
    }

    public Ice.AsyncResult begin_newSong(Song s, Ice.Callback __cb)
    {
        return begin_newSong(s, null, false, __cb);
    }

    public Ice.AsyncResult begin_newSong(Song s, java.util.Map<String, String> __ctx, Ice.Callback __cb)
    {
        return begin_newSong(s, __ctx, true, __cb);
    }

    public Ice.AsyncResult begin_newSong(Song s, Callback_ISongMonitor_newSong __cb)
    {
        return begin_newSong(s, null, false, __cb);
    }

    public Ice.AsyncResult begin_newSong(Song s, java.util.Map<String, String> __ctx, Callback_ISongMonitor_newSong __cb)
    {
        return begin_newSong(s, __ctx, true, __cb);
    }

    private Ice.AsyncResult begin_newSong(Song s, java.util.Map<String, String> __ctx, boolean __explicitCtx, IceInternal.CallbackBase __cb)
    {
        IceInternal.OutgoingAsync __result = new IceInternal.OutgoingAsync(this, __newSong_name, __cb);
        try
        {
            __result.__prepare(__newSong_name, Ice.OperationMode.Normal, __ctx, __explicitCtx);
            IceInternal.BasicStream __os = __result.__startWriteParams(Ice.FormatType.DefaultFormat);
            s.__write(__os);
            __result.__endWriteParams();
            __result.__send(true);
        }
        catch(Ice.LocalException __ex)
        {
            __result.__exceptionAsync(__ex);
        }
        return __result;
    }

    public void end_newSong(Ice.AsyncResult __result)
    {
        __end(__result, __newSong_name);
    }

    public static ISongMonitorPrx checkedCast(Ice.ObjectPrx __obj)
    {
        ISongMonitorPrx __d = null;
        if(__obj != null)
        {
            if(__obj instanceof ISongMonitorPrx)
            {
                __d = (ISongMonitorPrx)__obj;
            }
            else
            {
                if(__obj.ice_isA(ice_staticId()))
                {
                    ISongMonitorPrxHelper __h = new ISongMonitorPrxHelper();
                    __h.__copyFrom(__obj);
                    __d = __h;
                }
            }
        }
        return __d;
    }

    public static ISongMonitorPrx checkedCast(Ice.ObjectPrx __obj, java.util.Map<String, String> __ctx)
    {
        ISongMonitorPrx __d = null;
        if(__obj != null)
        {
            if(__obj instanceof ISongMonitorPrx)
            {
                __d = (ISongMonitorPrx)__obj;
            }
            else
            {
                if(__obj.ice_isA(ice_staticId(), __ctx))
                {
                    ISongMonitorPrxHelper __h = new ISongMonitorPrxHelper();
                    __h.__copyFrom(__obj);
                    __d = __h;
                }
            }
        }
        return __d;
    }

    public static ISongMonitorPrx checkedCast(Ice.ObjectPrx __obj, String __facet)
    {
        ISongMonitorPrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            try
            {
                if(__bb.ice_isA(ice_staticId()))
                {
                    ISongMonitorPrxHelper __h = new ISongMonitorPrxHelper();
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

    public static ISongMonitorPrx checkedCast(Ice.ObjectPrx __obj, String __facet, java.util.Map<String, String> __ctx)
    {
        ISongMonitorPrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            try
            {
                if(__bb.ice_isA(ice_staticId(), __ctx))
                {
                    ISongMonitorPrxHelper __h = new ISongMonitorPrxHelper();
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

    public static ISongMonitorPrx uncheckedCast(Ice.ObjectPrx __obj)
    {
        ISongMonitorPrx __d = null;
        if(__obj != null)
        {
            if(__obj instanceof ISongMonitorPrx)
            {
                __d = (ISongMonitorPrx)__obj;
            }
            else
            {
                ISongMonitorPrxHelper __h = new ISongMonitorPrxHelper();
                __h.__copyFrom(__obj);
                __d = __h;
            }
        }
        return __d;
    }

    public static ISongMonitorPrx uncheckedCast(Ice.ObjectPrx __obj, String __facet)
    {
        ISongMonitorPrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            ISongMonitorPrxHelper __h = new ISongMonitorPrxHelper();
            __h.__copyFrom(__bb);
            __d = __h;
        }
        return __d;
    }

    public static final String[] __ids =
    {
        "::Ice::Object",
        "::Player::ISongMonitor"
    };

    public static String ice_staticId()
    {
        return __ids[1];
    }

    protected Ice._ObjectDelM __createDelegateM()
    {
        return new _ISongMonitorDelM();
    }

    protected Ice._ObjectDelD __createDelegateD()
    {
        return new _ISongMonitorDelD();
    }

    public static void __write(IceInternal.BasicStream __os, ISongMonitorPrx v)
    {
        __os.writeProxy(v);
    }

    public static ISongMonitorPrx __read(IceInternal.BasicStream __is)
    {
        Ice.ObjectPrx proxy = __is.readProxy();
        if(proxy != null)
        {
            ISongMonitorPrxHelper result = new ISongMonitorPrxHelper();
            result.__copyFrom(proxy);
            return result;
        }
        return null;
    }

    public static final long serialVersionUID = 0L;
}
