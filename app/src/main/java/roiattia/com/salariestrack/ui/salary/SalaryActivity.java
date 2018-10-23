package roiattia.com.salariestrack.ui.salary;

import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.LocalDate;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import roiattia.com.salariestrack.R;
import roiattia.com.salariestrack.model.SalaryEntry;
import roiattia.com.salariestrack.utils.TextFormat;
import roiattia.com.salariestrack.widget.SalariesIntentService;

import static roiattia.com.salariestrack.utils.Constants.PAYMENT_DATE;
import static roiattia.com.salariestrack.utils.Constants.SALARY_DEFAULT_ID;
import static roiattia.com.salariestrack.utils.Constants.SALARY_ID_EXTRA;
import static roiattia.com.salariestrack.utils.Constants.WORK_DATE;

public class SalaryActivity extends AppCompatActivity {

    private static final String TAG = SalaryActivity.class.getSimpleName();

    private SalaryViewModel mViewModel;
    private boolean mIsConfirmed, mIsNewSalary;
    private SalaryEntry mSalaryEntry;

    @BindView(R.id.sw_is_paid) Switch mIsPaidSwitch;
    @BindView(R.id.sw_has_receipt) Switch mHasReceiptSwitch;
    @BindView(R.id.sw_has_contract) Switch mHasContractSwitch;
    @BindView(R.id.iet_name) TextInputEditText mNameInput;
    @BindView(R.id.iet_salary) TextInputEditText mSalaryInput;
    @BindView(R.id.iet_work_date) TextInputEditText mWorkDateInput;
    @BindView(R.id.iet_payment_date) TextInputEditText mPaymentDateInput;
    @BindView(R.id.iet_work_place) TextInputEditText mWorkPlaceInput;
    @BindView(R.id.iet_work_description) TextInputEditText mWorkDescriptionInput;
    @BindView(R.id.iv_name_error) ImageView mNameError;
    @BindView(R.id.iv_salary_error) ImageView mSalaryError;
    @BindView(R.id.iv_payment_date_error) ImageView mPaymentDateError;
    @BindView(R.id.tv_error_message) TextView mErrorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary);
        ButterKnife.bind(this);

        mSalaryEntry = new SalaryEntry();
        mIsConfirmed = true;

        setupViewModel();

        checkIntent();

        setupUI();
    }

    private void checkIntent(){
        long salaryId = getIntent().getLongExtra(SALARY_ID_EXTRA, SALARY_DEFAULT_ID);
        if(salaryId != SALARY_DEFAULT_ID){
            setTitle(getString(R.string.update_salary_title));
            mViewModel.getSalaryById(salaryId);
            updateSwitches(View.VISIBLE);
        } else {
            mIsNewSalary = true;
            setTitle(getString(R.string.new_salary_title));
            updateSwitches(View.GONE);
        }
    }

    private void setupUI() {
        mWorkDateInput.setFocusable(false);
        mWorkDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate(WORK_DATE);
            }
        });
        mPaymentDateInput.setFocusable(false);
        mPaymentDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate(PAYMENT_DATE);
            }
        });

        mIsPaidSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mSalaryEntry.setPaid(isChecked);
                if(isChecked){
                    mIsPaidSwitch.setText(getString(R.string.salary_paid_switch));
                } else {
                    mIsPaidSwitch.setText(getString(R.string.not_paid_switch));
                }
            }
        });

        mHasContractSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mSalaryEntry.setGotContract(isChecked);
                if(isChecked){
                    mHasContractSwitch.setText(getString(R.string.contract_approved_switch));
                } else {
                    mHasContractSwitch.setText(getString(R.string.no_contract_switch));
                }
            }
        });

        mHasReceiptSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mSalaryEntry.setGotReceipt(isChecked);
                if(isChecked){
                    mHasReceiptSwitch.setText(getString(R.string.receipt_switch));
                } else {
                    mHasReceiptSwitch.setText(getString(R.string.no_receipt_switch));
                }
            }
        });
    }


    private void updateSwitches(int visible) {
        mIsPaidSwitch.setVisibility(visible);
        mHasContractSwitch.setVisibility(visible);
        mHasReceiptSwitch.setVisibility(visible);
    }

    private void setupViewModel() {
        mViewModel = ViewModelProviders.of(this).get(SalaryViewModel.class);
        mViewModel.getMutableLiveDataSalary().observe(this, new Observer<SalaryEntry>() {
            @Override
            public void onChanged(@Nullable SalaryEntry salaryEntry) {
                if(salaryEntry != null){
                    mSalaryEntry = salaryEntry;
                    updateUI();
                }
            }
        });
    }

    private void updateUI() {
        mIsPaidSwitch.setChecked(mSalaryEntry.isPaid());
        mHasReceiptSwitch.setChecked(mSalaryEntry.isGotReceipt());
        mHasContractSwitch.setChecked(mSalaryEntry.isGotContract());
        mNameInput.setText(mSalaryEntry.getWorkerName());
        mSalaryInput.setText(TextFormat.getStringFormatFromDouble(mSalaryEntry.getSalary()));
        mWorkDateInput.setText(TextFormat.getDateStringFormat(mSalaryEntry.getWorkDate()));
        mPaymentDateInput.setText(TextFormat.getDateStringFormat(mSalaryEntry.getPaymentDate()));
        mWorkPlaceInput.setText(mSalaryEntry.getWorkPlace());
        mWorkDescriptionInput.setText(mSalaryEntry.getWorkDescription());
    }

    private void pickDate(final String paymentType) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String dateString = year + "-" + (month+1) + "-" + dayOfMonth;
                        if(paymentType.equals(WORK_DATE)){
                            mSalaryEntry.setWorkDate(LocalDate.parse(dateString));
                            mWorkDateInput.setText(TextFormat.
                                    getDateStringFormat(mSalaryEntry.getWorkDate()));
                        } else if(paymentType.equals(PAYMENT_DATE)){
                            mSalaryEntry.setPaymentDate(LocalDate.parse(dateString));
                            mPaymentDateInput.setText(TextFormat.
                                    getDateStringFormat(mSalaryEntry.getPaymentDate()));
                        }
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_salary, menu);
        if(mIsNewSalary){
            MenuItem menuItem = menu.findItem(R.id.mi_delete_salary);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mi_confirm_salary:
                confirmSalary();
                return true;
            case R.id.mi_delete_salary:
                showAlertDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set title
        alertDialogBuilder.setTitle(R.string.delete_salary_title);
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(R.string.yes,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        deleteSalary();
                    }
                })
                .setNegativeButton(R.string.no,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void deleteSalary() {
        mViewModel.deleteSalary(mSalaryEntry);
        SalariesIntentService.startActionProvideReport(this);
        finish();
    }

    private void confirmSalary() {
        if(mNameInput.getText().toString().trim().length() <= 0){
            setError(mNameError,false);
        } else {
            mSalaryEntry.setWorkerName(mNameInput.getText().toString().trim());
            setError(mNameError,true);
        }
        if(mSalaryInput.getText().toString().trim().length() <= 0){
            setError(mSalaryError,false);
        } else {
            try{
                mSalaryEntry.setSalary(Double.parseDouble(mSalaryInput.getText().toString()));
                setError(mSalaryError,true);
            } catch (Exception exception){
                setError(mSalaryError,false);;
            }
        }
        if(mPaymentDateInput.getText().toString().trim().length() <= 0){
            setError(mPaymentDateError,false);
        } else {
            setError(mPaymentDateError,true);
        }
        mSalaryEntry.setWorkPlace(mWorkPlaceInput.getText().toString().trim());
        mSalaryEntry.setWorkDescription(mWorkDescriptionInput.getText().toString().trim());
        if(mIsConfirmed){
            Toast.makeText(this, R.string.salary_saved, Toast.LENGTH_SHORT).show();
            mErrorText.setVisibility(View.INVISIBLE);
            mViewModel.insertSalary(mSalaryEntry);
            SalariesIntentService.startActionProvideReport(this);
            finish();
        } else {
            mErrorText.setVisibility(View.VISIBLE);
        }
    }

    private void setError(ImageView nameError, boolean isValid){
        if(isValid){
            nameError.setVisibility(View.INVISIBLE);
        } else {
            nameError.setVisibility(View.VISIBLE);
        }
        mIsConfirmed = isValid;
    }

}
