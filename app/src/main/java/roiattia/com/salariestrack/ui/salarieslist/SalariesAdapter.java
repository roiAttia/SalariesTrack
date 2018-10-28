package roiattia.com.salariestrack.ui.salarieslist;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.joda.time.LocalDate;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import roiattia.com.salariestrack.R;
import roiattia.com.salariestrack.model.SalaryListItem;
import roiattia.com.salariestrack.utils.TextFormat;

public class SalariesAdapter extends RecyclerView.Adapter<SalariesAdapter.SalaryViewHolder> {

    private Context mContext;
    private List<SalaryListItem> mSalariesList;
    private OnSalaryClickHandler mOnSalaryClickHandler;

    SalariesAdapter(Context context, OnSalaryClickHandler onSalaryClickHandler) {
        mContext = context;
        mOnSalaryClickHandler = onSalaryClickHandler;
    }

    public interface OnSalaryClickHandler{
        void onSalaryClick(long salaryId);
    }

    public void setSalariesData(List<SalaryListItem> salariesList){
        mSalariesList = salariesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SalaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.list_item_salary_new, parent, false);
        return new SalaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SalaryViewHolder holder, int position) {
        SalaryListItem salaryItem = mSalariesList.get(position);
        Locale current = mContext.getResources().getConfiguration().locale;
        Log.i("locale", Currency.getInstance(current).getSymbol());
        holder.mName.setText(salaryItem.getName());
//        holder.mSalary.setText(String.format("%s%s", mContext.getString(R.string.salary),
//                TextFormat.getStringFormatFromDouble(salaryItem.getSalary())));
        holder.mSalary.setText(TextFormat.getStringFormatFromDouble(salaryItem.getSalary()));
        holder.mCurrencySymbol.setText(Currency.getInstance(current).getSymbol());
        holder.mPaymentDate.setText(String.format("%s%s", mContext.getString(R.string.payment_date),
                TextFormat.getDateStringFormat(salaryItem.getPaymentDate())));
//        holder.mWorkDate.setText(String.format("%s%s", mContext.getString(R.string.work_date),
//                TextFormat.getDateStringFormat(salaryItem.getWorkDate())));
        holder.itemView.setBackgroundColor(getColorId(salaryItem.getPaymentDate()));
    }

    @Override
    public int getItemCount() {
        return mSalariesList == null ? 0 : mSalariesList.size();
    }

    class SalaryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_name) TextView mName;
        @BindView(R.id.tv_salary) TextView mSalary;
        @BindView(R.id.tv_payment_date) TextView mPaymentDate;
        @BindView(R.id.tv_currency_sumbol) TextView mCurrencySymbol;
//        @BindView(R.id.tv_work_date) TextView mWorkDate;

        SalaryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int salaryPosition = getAdapterPosition();
            mOnSalaryClickHandler.onSalaryClick(mSalariesList.get(salaryPosition).getSalaryId());
        }
    }

    private int getColorId(LocalDate paymentDate){
        int backgroundColor = Color.WHITE;
        LocalDate localDate = new LocalDate();
        if(paymentDate.equals(localDate)){ // check if the dates are equal
            backgroundColor = Color.RED;
        } else if(paymentDate.isBefore(localDate)){ // check if payment date has passed
            backgroundColor = Color.BLUE;
        } else if(paymentDate.getMonthOfYear() > localDate.getMonthOfYear() && // check if it's not at same month
                paymentDate.getYear() == localDate.getYear()){
            backgroundColor = Color.GREEN;
        } else if(paymentDate.plusDays(8).isAfter(localDate)){ // check if it's week before
            backgroundColor = Color.GRAY;
        }
        return Color.WHITE;
    }
}
