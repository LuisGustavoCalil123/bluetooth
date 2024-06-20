package com.example.bluetooth

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bluetooth.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val REQUEST_CODE_ENABLE_BT = 1
    private val REQUEST_CODE_DISCOVERABLE_BT = 2;

    private lateinit var bluetoothAdapter: BluetoothAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if (bluetoothAdapter == null) {
            binding.bluetoothStatus.text = "Não disponível"
        } else {
            binding.bluetoothStatus.text = "Disponível"
        }

        if (bluetoothAdapter.isEnabled) {
            binding.BluetoothImage.setImageResource(R.drawable.ic_bluetooth_on)
        } else

            binding.btnLigarB.setOnClickListener {
                if (!bluetoothAdapter.isEnabled) {
                    Toast.makeText(this, "Ligado", Toast.LENGTH_SHORT).show()
                } else {
                    val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    startActivityForResult(intent, REQUEST_CODE_ENABLE_BT)
                }
            }

        binding.btnDesligarB.setOnClickListener {
            if (!bluetoothAdapter.isEnabled) {

            } else {
                bluetoothAdapter.disable()
                binding.BluetoothImage.setImageResource(R.drawable.ic_bluetooth_off)
                Toast.makeText(this, "Bluetooth Desligado", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnMeuB.setOnClickListener {
            if (bluetoothAdapter.isDiscovering) {
                Toast.makeText(this, "Este é meu dispositivo", Toast.LENGTH_SHORT).show()
                val intent = Intent(Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE))
                startActivityForResult(intent, REQUEST_CODE_DISCOVERABLE_BT)

            }
        }
        binding.btnAparelhoC.setOnClickListener {
            if (bluetoothAdapter.isEnabled){
                binding.statusConectados.text = "Dispositivos conectados:"
                val devices = bluetoothAdapter.bondedDevices
                for (device in devices){
                    val deviceName = device.name
                    val deviceAdress = device
                    binding.statusConectados.append("\n Dispositivos: " + "$deviceName $device")
                }
            }else{
                Toast.makeText(this, "Favor ligar o Bluetooth", Toast.LENGTH_SHORT).show()
            }
        }

        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            when(requestCode){
                REQUEST_CODE_ENABLE_BT ->
                    if (resultCode == Activity.RESULT_OK){
                        binding.BluetoothImage.setImageResource(R.drawable.ic_bluetooth_on)
                        Toast.makeText(this, "Bluetooth Ligado", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "Bluetooth Desligado", Toast.LENGTH_SHORT).show()
                    }
            }
            super.onActivityResult(requestCode, resultCode, data)
        }

    }

}