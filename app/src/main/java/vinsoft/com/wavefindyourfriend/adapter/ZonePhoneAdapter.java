package vinsoft.com.wavefindyourfriend.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import vinsoft.com.wavefindyourfriend.R;
import vinsoft.com.wavefindyourfriend.model.ZonePhone;
import vinsoft.com.wavefindyourfriend.myinterface.ISetZonePhone;

/**
 * Created by DONG on 04-Apr-17.
 */

public class ZonePhoneAdapter extends RecyclerView.Adapter<ZonePhoneAdapter.ViewHolder> {
    List<ZonePhone> zonePhoneList;
    ISetZonePhone iSetZonePhone = null;

    public ZonePhoneAdapter() {
    }

    public ZonePhoneAdapter(List<ZonePhone> zonePhoneList, ISetZonePhone iSetZonePhone) {
        this.zonePhoneList = zonePhoneList;
        this.iSetZonePhone = iSetZonePhone;
    }

    public List<ZonePhone> getZonePhoneList() {
        return zonePhoneList;
    }

    public void setZonePhoneList(List<ZonePhone> zonePhoneList) {
        this.zonePhoneList = zonePhoneList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_zonephone_layout, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ZonePhone zonePhone = zonePhoneList.get(position);
        if (position % 2 == 0)
            holder.llItem.setBackgroundColor(Color.parseColor("#0d948d"));
        else
            holder.llItem.setBackgroundColor(Color.parseColor("#3fafaa"));
        holder.tvCountryName.setText(zonePhone.getCountryName());
    }

    @Override
    public int getItemCount() {
        return zonePhoneList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCountryName;
        LinearLayout llItem;

        public ViewHolder(View itemView) {
            super(itemView);

            tvCountryName = (TextView) itemView.findViewById(R.id.tv_country_name);
            llItem = (LinearLayout) itemView.findViewById(R.id.ll_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iSetZonePhone.onSetZonePhone(zonePhoneList.get(getAdapterPosition()));
                }
            });
        }
    }
}