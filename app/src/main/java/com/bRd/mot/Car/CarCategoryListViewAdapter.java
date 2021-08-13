package com.bRd.mot.Car;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bRd.mot.Entity.CarCategory;
import com.bRd.mot.Helper.DateHelper;
import com.bRd.mot.R;
import com.bRd.mot.Utils.DatabaseHelper;
import com.bRd.mot.Utils.Utility;

import java.util.ArrayList;
import java.util.Calendar;

public class CarCategoryListViewAdapter extends BaseAdapter {

    private DatabaseHelper dbHelper;
    private Context context;
    private CarCategoryFragment carCategoryFragment;
    private ArrayList<CarCategory> carCategoryList;

    CarCategoryListViewAdapter(CarCategoryFragment carCategoryFragment, ArrayList<CarCategory> carCategoryList) {
        this.carCategoryFragment = carCategoryFragment;
        this.carCategoryList = carCategoryList;
        this.context = carCategoryFragment.getContext();
        dbHelper = new DatabaseHelper(context);
    }

    @Override
    public int getCount() {
        return carCategoryList.size();
    }

    @Override
    public Object getItem(int i) {
        return carCategoryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return carCategoryList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        CarCategory carCategory = carCategoryList.get(i);

        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (layoutInflater != null)
            view = layoutInflater.inflate(R.layout.list_item_car_category, null);

        TextView nameTextView = view.findViewById(R.id.name_tv);
        nameTextView.setText(carCategory.getName());
        TextView fromDateToDateTextView = view.findViewById(R.id.fromDateToDateTextView);
        TextView sumTextView = view.findViewById(R.id.sum_tv);

        if (carCategory.getPaidDate() != null && carCategory.getDeadlineDate() != null)
            fromDateToDateTextView.setText(context.getResources().getString(R.string.placeholder_3,
                    DateHelper.formatDateToString(carCategory.getPaidDate()),
                    "-", DateHelper.formatDateToString(carCategory.getDeadlineDate())));

        ImageView imageView = view.findViewById(R.id.imageView);

        if (carCategory.isPaid() && Calendar.getInstance().getTime().before(
                DateHelper.getDateBeforeSevenDays(carCategory.getDeadlineDate()))) {
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.success));
            sumTextView.setText(context.getResources().getString(R.string.placeholder_2, Utility.formatDoubleToString(carCategory.getSum()), "лв."));
        }

        if (!carCategory.isPaid() || (carCategory.getDeadlineDate() != null &&
                Calendar.getInstance().getTime().after(carCategory.getDeadlineDate()))) {

            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.error));
            fromDateToDateTextView.setText("Не сте заплатили!");

            carCategory.setPaid(false);
            carCategory.setPaidDate(null);
            carCategory.setDeadlineDate(null);
            carCategory.setSum(0.00);
            dbHelper.editCarCategory(carCategory);

        } else if (carCategory.isPaid() && carCategory.getDeadlineDate() != null && Calendar.getInstance().getTime().after(
                DateHelper.getDateBeforeSevenDays(carCategory.getDeadlineDate()))) {
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.warning));
            sumTextView.setText(context.getResources().getString(R.string.placeholder_2, Utility.formatDoubleToString(carCategory.getSum()), "лв."));
        }


        return view;
    }
}
