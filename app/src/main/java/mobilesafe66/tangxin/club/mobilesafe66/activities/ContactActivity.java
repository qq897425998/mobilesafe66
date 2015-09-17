package mobilesafe66.tangxin.club.mobilesafe66.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import mobilesafe66.tangxin.club.mobilesafe66.R;

public class ContactActivity extends Activity {
    private ListView lvContact;

    private void assignViews() {
        lvContact = (ListView) findViewById(R.id.lv_contact);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        assignViews();


        final ArrayList<HashMap<String, String>> list = readContact();
        lvContact.setAdapter(new SimpleAdapter(this, list,
                R.layout.list_item_contact, new String[] { "name", "phone" },
                new int[] { R.id.tv_name, R.id.tv_phone }));
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HashMap<String,String> map = list.get(position);
                String phone = map.get("phone");

                Intent data = new Intent();
                data.putExtra("phone",phone);
                setResult(0, data);
                finish();
            }
        });
    }

    /**
     * @return
     * com.android.provider.contact/databases/contacts.2.db
     * raw_contacts ,data, mimetye
     * 1.先从raw_contacts中读去 联系人contact_id
     * 2.通过contact_id看到 联系人相关信息
     * 3.根据mimetype_id 查询mimetype表,得天信息(号码,名字)
     */
    private ArrayList<HashMap<String, String>> readContact() {

        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        Cursor rawContactCursor = getContentResolver().query(Uri.parse("content://com.android.contacts/raw_contacts"), new String[]{"contact_id"}, null, null, null);
        while (rawContactCursor.moveToNext()) {
            String contactId = rawContactCursor.getString(0);

            //这里指定data   系统底屋会先找view_data 这个视图(视图是多个表结合，视图不是表)  mimetype_id 在视图中的字段名是mimetype
            Cursor dataCursor = getContentResolver().query(                                                     //where  raw_contact_id = contactId
                    Uri.parse("content://com.android.contacts/data"), new String[]{"data1", "mimetype"}, "raw_contact_id=?", new String[]{contactId}, null);

            HashMap<String, String> map = new HashMap<>();
            while (dataCursor.moveToNext()) {
                String data = dataCursor.getString(0);
                String mimeype = dataCursor.getString(1);

                if(!TextUtils.isEmpty(data)){
                    if ("vnd.android.cursor.item/phone_v2".equals(mimeype)) {
                        map.put("phone", data.replace(" ",""));
                    } else if ("vnd.android.cursor.item/name".equals(mimeype)) {
                        map.put("name", data);
                    }
                }
            }
            if (!TextUtils.isEmpty(map.get("name")) && !TextUtils.isEmpty(map.get("phone")))
                list.add(map);
        }
        return list;
    }
}
