package ru.rubiconepro.study.Modules.NoteBook.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import ru.rubiconepro.study.Modules.NoteBook.Layout.Part;
import ru.rubiconepro.study.Modules.NoteBook.Layout.PartAdd;
import ru.rubiconepro.study.Modules.NoteBook.Model.PartListModel;
import ru.rubiconepro.study.Modules.NoteBook.Model.PartModel;
import ru.rubiconepro.study.R;

public class PartAdapter extends BaseAdapter implements View.OnClickListener {

    PartListModel data;
    Context context;

    LayoutInflater inflater;

    boolean isEditable;

    public PartAdapter(PartListModel data, Context context) {
        this.data = data;
        this.context = context;

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setEditable(boolean editable) {
        isEditable = editable;
    }

    @Override
    public int getCount() {
        return data.items.size();
    }

    @Override
    public Object getItem(int position) {
        return data.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.items.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.notebook_part_call, parent, false);
        }

        TextView label = convertView.findViewById(R.id.label);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);
        Button btnEdit   = convertView.findViewById(R.id.btnEdit);

        if (!isEditable) {
            btnDelete.setVisibility(View.GONE);
            btnEdit.setVisibility(View.GONE);
        } else {
            btnDelete.setVisibility(View.VISIBLE);
            btnEdit.setVisibility(View.VISIBLE);
        }

        //Добавляем тагом позицию
        btnDelete.setTag(position);
        btnEdit.setTag(position);

        //Вешаем обработчики
        btnDelete.setOnClickListener(this);
        btnEdit.setOnClickListener(this);

        label.setText(data.items.get(position).toString());

        return convertView;
    }

    @Override
    public void onClick(View v) {
        int position = (int)v.getTag();
        if (v.getId() == R.id.btnDelete)
            this.deleteElement(position);
        if (v.getId() == R.id.btnEdit)
            this.editElement(position);
    }

    private void deleteElement(int position) {
        data.items.remove(position);
        notifyDataSetChanged();
    }

    private void editElement(int position) {
        Intent i = new Intent(context, PartAdd.class);
        i.putExtra("position", position);
        i.putExtra("model", data.items.get(position));
        ((Part)context).startActivityForResult(i, 0);
    }
}
