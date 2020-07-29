package com.etcmobileapps.nobetcieczaneler.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.etcmobileapps.nobetcieczaneler.Api.ApiClient;
import com.etcmobileapps.nobetcieczaneler.Api.CustomListViewAdapter;
import com.etcmobileapps.nobetcieczaneler.Api.Repo;
import com.etcmobileapps.nobetcieczaneler.Api.RestInterface;
import com.etcmobileapps.nobetcieczaneler.R;
import com.etcmobileapps.nobetcieczaneler.View.MainActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCountry extends Fragment {
     String sehirAdi,ilceAdi;
     ListView eczaneList;
    RestInterface[] restInterface;
    ArrayList<Repo> eczaneListesi;
    CustomListViewAdapter listViewAdapter[];
    //Spinner içerisine koyacağımız verileri tanımlıyoruz.
    private String[] iller={"İSTANBUL","TEKİRDAĞ"};
    private String[] ilceler0={"ADALAR","ARNAVUTKÖY","ATAŞEHİR","AVCILAR","BAğCILAR","BAHÇELİEVLER","BAKIRKÖY","BAŞAKŞEHİR","BAYRAMPAŞA","BEŞİKTAŞ","BEYLİKDÜZÜ","BEYOĞLU","BÜYÜKÇEKMECE","BEYKOZ","ÇATALCA","ÇEKMEKÖY","ESENLER","ESENYURT","EYÜP","FATİH","GAZİOSMANPAŞA","GÜNGÖREN","KADIKÖY","KAĞITHANE","KARTAL","KÜÇÜKÇEKMECE","MALTEPE","PENDİK","SANCAKTEPE","SARIYER","SİLİVRİ","SULTANBEYLİ","SULTANGAZİ","ŞİLE","ŞİŞLİ","TUZLA","ÜSKÜDAR","ÜMRANİYE","ZEYTİNBURNU"};
   // private String[] ilceler1={"AKYURT","ALTINDAĞ","AYAŞ","BALA","BEYPAZARI","ÇAMLIDERE","ÇANKAYA","ÇUBUK","ELMADAĞ","ETİMESGUT","EVREN","GÖLBAŞI","GÜDÜL","HAYMANA","KALECİK","KAZAN","KEÇİÖREN","KIZILCAHAMAM","MAMAK","NALLIHAN","POLATLI","PURSAKLAR","SİNCAN","ŞEREFLİKOÇHİSAR","YENİMAHALLE"};
    private String[] ilceler1= {"ÇERKEZKÖY", "ÇORLU", "ERGENE", "HAYRABOLU", "KAPAKLI", "MALKARA", "MARMARAEREĞLİSİ", "MURATLI", "SARAY", "SÜLEYMANPAŞA", "ŞARKÖY", "YENİÇİFTLİK"};
    //Spinner'ları ve Adapter'lerini tanımlıyoruz.
    private Spinner spinnerIller;
    private Spinner spinnerIlceler;
    private ArrayAdapter<String> dataAdapterForIller;
    private ArrayAdapter<String> dataAdapterForIlceler;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_country,container,false);
        return view;

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        //xml kısmında hazırladığımğız spinnerları burda tanımladıklarımızla eşleştiriyoruz.
        spinnerIller = (Spinner) getActivity().findViewById(R.id.spinner1);
        spinnerIlceler = (Spinner) getActivity().findViewById(R.id.spinner2);

        //Spinner'lar için adapterleri hazırlıyoruz.
        dataAdapterForIller = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, iller);
        dataAdapterForIlceler = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,ilceler0);

        //Listelenecek verilerin görünümünü belirliyoruz.
        dataAdapterForIller.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterForIlceler.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Hazırladğımız Adapter'leri Spinner'lara ekliyoruz.
        spinnerIller.setAdapter(dataAdapterForIller);
        spinnerIlceler.setAdapter(dataAdapterForIlceler);

        // Listelerden bir eleman seçildiğinde yapılacakları tanımlıyoruz.
        spinnerIller.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //Hangi il seçilmişse onun ilçeleri adapter'e ekleniyor.
                if(parent.getSelectedItem().toString().equals(iller[0]))
                    dataAdapterForIlceler = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,ilceler0);
                else if(parent.getSelectedItem().toString().equals(iller[1]))
                    dataAdapterForIlceler = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,ilceler1);

                dataAdapterForIlceler.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerIlceler.setAdapter(dataAdapterForIlceler);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerIlceler.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //Seçilen il ve ilçeyi ekranda gösteriyoruz.
                sehirAdi = spinnerIller.getSelectedItem().toString();
                ilceAdi = parent.getSelectedItem().toString();

                eczaneVericek(sehirAdi,ilceAdi);
                Toast.makeText(getActivity(), sehirAdi + ilceAdi, Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        eczaneList = getActivity().findViewById(R.id.eczaneList);

         eczaneListesi = new ArrayList<>();
         restInterface = new RestInterface[1];
       restInterface[0] = ApiClient.getClient("https://eczaci-api.herokuapp.com/").create(RestInterface.class);




    }

    public void yaziyiDegis(String gelenVeri) {

    }



    public void eczaneVericek(String sehir, String ilce) {
        CustomListViewAdapter listViewAdapter[] = new CustomListViewAdapter[1];
      // String toastUrl = "\"https://eczaci-api.herokuapp.com/get/\" + sehir + \"/\" + ilce";
      //  Toast.makeText(getActivity(), toastUrl, Toast.LENGTH_SHORT).show();
        Call<List<Repo>> call = restInterface[0].eczaneListele("https://eczaci-api.herokuapp.com/get/" + sehir + "/" + ilce);
        call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {

                eczaneListesi.clear();
                eczaneListesi.addAll(response.body());


                if (eczaneListesi.size() > 0) {
                    listViewAdapter[0] = new CustomListViewAdapter(getActivity(), eczaneListesi);
                    eczaneList.setAdapter(listViewAdapter[0]);
                }

                eczaneList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                        // Herhangi bir depreme tıklandığında çalışacak olan bölüm
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                Toast.makeText(getActivity(), "Veri çekilemedi.", Toast.LENGTH_SHORT).show();
            }
        });


    }

}