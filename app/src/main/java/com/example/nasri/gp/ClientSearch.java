package com.example.nasri.gp;


import android.widget.Filter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hp on 3/17/2016.
 */
public class ClientSearch extends Filter {

    ClientsAdapter adapter;
    List<UserInfo> filterList;


    public ClientSearch(List<UserInfo> filterList, ClientsAdapter adapter) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if (constraint != null && constraint.length() > 0) {
            //CHANGE TO UPPER
            constraint = constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<UserInfo> filteredPlayers = new ArrayList<>();

            for (int i = 0; i < filterList.size(); i++) {
                //CHECK
                if (filterList.get(i).getUserName().toUpperCase().contains(constraint)) {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredPlayers.add(filterList.get(i));
                }
                if (filterList.get(i).getPhone().toUpperCase().contains(constraint)) {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredPlayers.add(filterList.get(i));
                }
            }

            results.count = filteredPlayers.size();
            results.values = filteredPlayers;
        } else {
            results.count = filterList.size();
            results.values = filterList;

        }


        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.fieldItems = (List<UserInfo>) results.values;

        //REFRESH
        adapter.notifyDataSetChanged();
    }
}