package ebills.screens;

import java.io.File;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.apathon.ebills.App;
import com.apathon.ebills.R;

import ebills.models.Seller;

public class MakeSellerActivity extends Activity implements View.OnClickListener {


    @SuppressWarnings("unchecked")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.button)));
        setContentView(R.layout.make_seller);
        HashMap<String, String> mList=(HashMap<String, String>) getIntent().getExtras().getSerializable("info");
        findViewById(R.id.saveButton).setOnClickListener(this);
        getEditSellerCity().setText(getValues(mList, "CITY"));
        getEditSellerAddress().setText(getValues(mList, "TIN"));
        getEditSellerState().setText(getValues(mList, "STATE"));
        getEditINvoiceNumber().setText(getValues(mList, "Invoice"));
        fileName=getIntent().getExtras().getString("path");
        
    }
    
    String fileName;
    
    
    public String getValues(HashMap<String, String> mList,String key){
    	
    	if(mList.get(key)==null||mList.get(key).equalsIgnoreCase(""))
    		return "";
    	else
    		return mList.get(key);
    }

    private EditText getEditSellerName(){
        return (EditText) findViewById(R.id.editSellerName);
    }

    private EditText getEditSellerAddress(){
        return (EditText) findViewById(R.id.editSellerAddress);
    }

    private EditText getEditSellerCity(){
        return (EditText) findViewById(R.id.editSellerCity);
    }

    private EditText getEditSellerPin(){
        return (EditText) findViewById(R.id.editSellerPin);
    }
    private EditText getEditSellerState(){
        return (EditText) findViewById(R.id.editSellerState);
    }
    private EditText getEditINvoiceNumber(){
        return (EditText) findViewById(R.id.editSellerInvoice);
    }
    
    

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.saveButton:
                Seller  seller = new Seller();
                seller.setColumn_seller(getEditSellerName().getText().toString());
                seller.setColumn_seller_address(getEditSellerAddress().getText().toString());
                seller.setColumn_seller_city(getEditSellerCity().getText().toString());
                seller.setColumn_seller_pin(getEditSellerPin().getText().toString());
                seller.setColumn_seller_state(getEditSellerState().getText().toString());
                long l = App.getDb().insertSeller(seller);
                if (l!=-1)
                {
                    Toast.makeText(getApplicationContext(),"Seller Inserted successfully.",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MakeItemActivity.class);
                    //TODO
                    File mFile=new File(fileName);
                    intent.putExtra("imagePath", fileName);
                    intent.putExtra("imageName",mFile.getName());
                    intent.putExtra("imageSellerId", l);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Oops something went wrong!",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
