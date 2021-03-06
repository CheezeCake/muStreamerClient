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
// Generated from file `pocketSphinx.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package PocketSphinxIce;

public final class IPocketSphinxServerHolder extends Ice.ObjectHolderBase<IPocketSphinxServer>
{
    public
    IPocketSphinxServerHolder()
    {
    }

    public
    IPocketSphinxServerHolder(IPocketSphinxServer value)
    {
        this.value = value;
    }

    public void
    patch(Ice.Object v)
    {
        if(v == null || v instanceof IPocketSphinxServer)
        {
            value = (IPocketSphinxServer)v;
        }
        else
        {
            IceInternal.Ex.throwUOE(type(), v);
        }
    }

    public String
    type()
    {
        return _IPocketSphinxServerDisp.ice_staticId();
    }
}
