package com.example.qiwi_changellenge_it_amnesia.ui.readQR

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.example.qiwi_changellenge_it_Amnesia.R
import com.example.qiwi_changellenge_it_amnesia.App
import com.example.qiwi_changellenge_it_amnesia.domain.models.PaymentBody
import com.example.qiwi_changellenge_it_amnesia.domain.models.Product
import com.example.qiwi_changellenge_it_amnesia.mvp.BaseFragment
import com.example.qiwi_changellenge_it_amnesia.ui.activity.ScanActivity
import kotlinx.android.synthetic.main.read_qr_fragment.*


class ReadQRFragment: BaseFragment<ReadQRPresenterImpl>(), ReadQRView {

    override fun createComponent() {
        App.instance
            .getAppComponent()
            .createReadQRFragment()
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.read_qr_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.start()
        presenter.view = this
        checkPermissionGranted()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        if (this.isVisible) {
            exampleProductsRecyclerView.layoutManager as GridLayoutManager
            val productList: MutableList<Product> = mutableListOf(Product( 1000),Product(500), Product(1999), Product(7499) )
            val imageListPetTypes = intArrayOf(R.drawable.car_foreground, R.drawable.motorcycle_foreground, R.drawable.rocket_foreground, R.drawable.robot_foreground )
            val adapter = ReadQRProductAdapter(productList,imageListPetTypes)
            exampleProductsRecyclerView.adapter = adapter
            adapter.setOnClickRecyclerListener(object : ReadQRProductAdapter.OnClickListener {
                override fun onClick(position: Int) {
                    amount = productList[position].amount
                    val intent = Intent(requireActivity(), ScanActivity::class.java)
                    startActivity(intent)
                }
            })
        }
    }

    private fun checkPermissionGranted() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED){
            val permissions = arrayOf(Manifest.permission.CAMERA)
            ActivityCompat.requestPermissions(requireActivity(), permissions,0)
        }
    }

    override fun onResume() {
        super.onResume()
        if(scanQR && paymentToken.length>20) {
            presenter.sendTransaction(PaymentBody(amount.toString(), paymentToken))
        }
    }

    override fun successPay() {
        MaterialDialog.Builder(requireContext())
            .content(getString(R.string.OperationSuccess))
            .titleColor(requireActivity().getColor(R.color.black))
            .positiveText(R.string.dismiss)
            .positiveColor(requireActivity().getColor(R.color.mainColor))
            .onPositive { materialDialog, _ ->
                materialDialog.dismiss()
                findNavController().navigate(R.id.action_readQRFragment_to_profileFragment)
            }.show()
    }

    override fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    override fun showError(message: String?): Unit = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

    companion object{
        var scanQR = false
        var paymentToken = ""
        var amount: Long = 0
    }
}