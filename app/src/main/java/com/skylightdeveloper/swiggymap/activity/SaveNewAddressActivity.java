package com.skylightdeveloper.swiggymap.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.skylightdeveloper.swiggymap.Constant.SConstant;
import com.skylightdeveloper.swiggymap.R;
import com.skylightdeveloper.swiggymap.model.UserSavedAddressModel;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Akash Wangalwar on 24-09-2016.
 */
public class SaveNewAddressActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = SaveNewAddressActivity.class.getSimpleName();
    private ImageView mOtherTagIv, mHomeTagIv, mWorkTagIv;
    private View mOtherAddressTypeView;
    private EditText mOtherTypeET, mStreetAddressET, mLandmarkAddressET, mFlatAddressET;
    private Button mSaveBtn;
    private boolean mIsWorkAddressAlreadyCreated = true, mIsHomeressAlreadyCreated;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.save_new_address_activity_layout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setIdsToViews();
        setListenerToViews();

        getIntentValues();
    }

    private void getIntentValues() {

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            boolean isNewAddress = bundle.getBoolean(SConstant.NEWADDRESS);

            setDataToView(isNewAddress);
        }
    }

    private void setDataToView(boolean isNewAddress) {

        String userAddId = getIntent().getExtras().getString(SConstant.USER_ADD_ID);
        String add = getIntent().getExtras().getString(SConstant.ADDRESS);

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmQuery<UserSavedAddressModel> query = realm.where(UserSavedAddressModel.class);

        RealmResults<UserSavedAddressModel> list = query.findAll();
        realm.commitTransaction();

        for (UserSavedAddressModel item : list) {

            if (item.isHomeAdress()) {
                mIsHomeressAlreadyCreated = true;
            } else if (item.isWorkAdress()) {
                mIsWorkAddressAlreadyCreated = true;
            }
        }

        mStreetAddressET.setText(add);
        if (mIsHomeressAlreadyCreated) {
            mHomeTagIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.home_disable));
        }
        if (mIsWorkAddressAlreadyCreated) {
            mWorkTagIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.work_disable));
        }
    }

    private void setIdsToViews() {

        mOtherTypeET = (EditText) findViewById(R.id.address_type_et_id);
        mStreetAddressET = (EditText) findViewById(R.id.street_add_et_id);
        mFlatAddressET = (EditText) findViewById(R.id.flat_num_add_et_id);
        mLandmarkAddressET = (EditText) findViewById(R.id.landmark_add_et_id);

        mOtherTagIv = (ImageView) findViewById(R.id.other_tag_iv_id);
        mHomeTagIv = (ImageView) findViewById(R.id.home_tag_iv_id);
        mWorkTagIv = (ImageView) findViewById(R.id.work_tag_iv_id);

        mOtherAddressTypeView = (View) findViewById(R.id.address_type_view_id);

        mSaveBtn = (Button) findViewById(R.id.save_btn_id);
    }

    private void setListenerToViews() {

        mOtherTagIv.setOnClickListener(this);
        mHomeTagIv.setOnClickListener(this);
        mWorkTagIv.setOnClickListener(this);

        mSaveBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view == mOtherTagIv) {

            switchOtherTagImage();
            return;
        }
        if (view == mWorkTagIv) {

            if (mIsWorkAddressAlreadyCreated) {
                showLongToast("Sorry, already have WORK address");
            } else {
                switchWorkTagImage();
            }
            return;
        }
        if (view == mHomeTagIv) {

            if (mIsHomeressAlreadyCreated) {
                showLongToast("Sorry, already have HOME address");
            } else {
                switchHomeTagImage();
            }
            return;
        }
    }

    private void switchWorkTagImage() {
        if (mIsWorkAddressAlreadyCreated) {
            return;
        }
//        switchHomeTagImage();
//        switchOtherTagImage();
        if (mWorkTagIv.isSelected()) {
            mWorkTagIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.work));
            mOtherAddressTypeView.setVisibility(View.GONE);
            mWorkTagIv.setSelected(false);
        } else {
            mWorkTagIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.work_selected));
            mWorkTagIv.setSelected(true);
        }


        if (mOtherTagIv.isSelected()) {
            mOtherTagIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.other_selected));
            mOtherAddressTypeView.setVisibility(View.VISIBLE);
            mOtherTypeET.requestFocus();
            mOtherTagIv.setSelected(false);
        } else {
            mOtherTagIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.other));
            mOtherAddressTypeView.setVisibility(View.GONE);
            mOtherTagIv.setSelected(true);
        }
        if (mIsHomeressAlreadyCreated) {
            return;
        }
        if (mHomeTagIv.isSelected()) {
            mHomeTagIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.home));
            mOtherAddressTypeView.setVisibility(View.GONE);
            mHomeTagIv.setSelected(false);
        } else {
            mHomeTagIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.home_seleced));
            mHomeTagIv.setSelected(true);
        }
    }

    private void switchOtherTagImage() {
        switchWorkTagImage();
        switchHomeTagImage();
        if (mOtherTagIv.isSelected()) {
            mOtherTagIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.other_selected));
            mOtherAddressTypeView.setVisibility(View.VISIBLE);
            mOtherTypeET.requestFocus();
            mOtherTagIv.setSelected(false);
        } else {
            mOtherTagIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.other));
            mOtherAddressTypeView.setVisibility(View.GONE);
            mOtherTagIv.setSelected(true);
        }
    }

    private void switchHomeTagImage() {

        if (mIsHomeressAlreadyCreated) {
            return;
        }
        switchOtherTagImage();
        switchWorkTagImage();
        if (mHomeTagIv.isSelected()) {
            mHomeTagIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.home));
            mOtherAddressTypeView.setVisibility(View.GONE);
            mHomeTagIv.setSelected(false);
        } else {
            mHomeTagIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.home_seleced));
            mHomeTagIv.setSelected(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
