package com.skylightdeveloper.swiggymap.interfaces;

import com.skylightdeveloper.swiggymap.model.Address;

/**
 * Created by Akash Wangalwar on 23-09-2016.
 */
public interface AddressInterface  {
    public void onSuccess(Address address);
    public void onfailure(String errorMessage);
}
