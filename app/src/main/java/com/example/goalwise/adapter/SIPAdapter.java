package com.example.goalwise.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;

import com.example.goalwise.R;
import com.example.goalwise.activity.MainActivity;
import com.example.goalwise.model.DataDto;

import java.util.List;

public class SIPAdapter extends ArrayAdapter<DataDto> {

    private List<DataDto> list;
    private Context context;
    private LayoutInflater inflater;
    private MainActivity.ShowDialogBoxCallBack showDialogBoxCallBack;

    public SIPAdapter(Context context, int resource, List<DataDto> list, MainActivity.ShowDialogBoxCallBack showDialogBoxCallBack) {
        super(context, resource, list);
        this.list = list;
        this.context = context;
        this.showDialogBoxCallBack = showDialogBoxCallBack;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull final ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.sip_list, null);
            holder = new ViewHolder();

            holder.sipName = convertView.findViewById(R.id.sipName);
            holder.amount = convertView.findViewById(R.id.amount);
            holder.multiple = convertView.findViewById(R.id.multiple);
            holder.addTv = convertView.findViewById(R.id.addTv);
            holder.dateTv = convertView.findViewById(R.id.dateTv);
            holder.sipAmount = convertView.findViewById(R.id.sipAmount);
            holder.addFundBtn = convertView.findViewById(R.id.addFundBtn);
            holder.sipAmountLl = convertView.findViewById(R.id.sipAmountLl);
            holder.errorMsg = convertView.findViewById(R.id.errorMsg);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final DataDto item = getItem(position);

        if(item != null){
            holder.sipName.setText(item.getFundname());
            String amountStr = context.getString(R.string.min_sip_amount) +
                    context.getString(R.string.space) + context.getString(R.string.rs) +context.getString(R.string.space) + item.getMinsipamount() ;
            holder.amount.setText(amountStr);

            String multipleStr = context.getString(R.string.min_sip_multiple) +
                    context.getString(R.string.space) + context.getString(R.string.rs) +context.getString(R.string.space) + item.getMinsipmultiple() ;
            holder.multiple.setText(multipleStr);

            holder.dateTv.setText(getDateStr(item.getSipdates()));

            ViewHolder finalHolder = holder;
            holder.addTv.setOnClickListener(view -> {
                int visibility = finalHolder.sipAmountLl.getVisibility();
                if(visibility == View.VISIBLE){
                    finalHolder.sipAmountLl.setVisibility(View.GONE);
                    finalHolder.addTv.setVisibility(View.VISIBLE);
                }else {
                    finalHolder.sipAmountLl.setVisibility(View.VISIBLE);
                    finalHolder.addTv.setVisibility(View.GONE);
                    finalHolder.sipAmount.setText("");
                }
            });

            ViewHolder finalHolder1 = holder;
            holder.addFundBtn.setOnClickListener(view -> {
                String sipAmount =  finalHolder1.sipAmount.getText().toString();
                if(sipAmount.length() > 0){
                    long parseInt = Long.parseLong(sipAmount);
                    if(parseInt > item.getMinsipamount() && parseInt % item.getMinsipmultiple() == 0){
                        finalHolder.errorMsg.setVisibility(View.GONE);
                        showDialogBoxCallBack.showDialogBox(item.getFundname());
                        finalHolder1.addTv.performClick();
                    }else {
                        finalHolder.errorMsg.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        return convertView;
    }

    private String getDateStr(List<Integer> sipDates) {
        String sipDate = context.getString(R.string.sip_dates) + context.getString(R.string.space);
        if(sipDates != null && sipDates.size() > 0){
            for(int i = 0; i < sipDates.size(); i ++){
                sipDate = sipDate.concat(i + "");

                if(i != sipDates.size() - 1){
                    sipDate = sipDate.concat("," + context.getString(R.string.space));
                }
            }
        }
        return sipDate.concat(context.getString(R.string.space)).concat(context.getString(R.string.of_every_month));
    }

    @Override
    public int getCount() {
        if (this.list != null && this.list.size() > 0) {
            return this.list.size();
        } else {
            return 0;
        }
    }

    private class ViewHolder {
        private TextView sipName,amount,multiple,addTv,dateTv,errorMsg;
        private EditText sipAmount;
        private Button addFundBtn;
        private LinearLayout sipAmountLl;
    }
}
