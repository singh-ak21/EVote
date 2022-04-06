package in.cuchd.android.evote;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class CommissionerMenuFragment extends Fragment
{
    private CardView mVoterList;

    public static Fragment newInstance()
    {
        return new CommissionerMenuFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_commissioner_menu, container, false);

        mVoterList = view.findViewById(R.id.voter_list);

        return view;
    }
}
