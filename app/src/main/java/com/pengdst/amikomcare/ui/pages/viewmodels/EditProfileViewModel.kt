package com.pengdst.amikomcare.ui.pages.viewmodels

import com.pengdst.amikomcare.datas.models.DokterModel

class EditProfileViewModel : BaseMainViewModel(){

    fun updateDokter(dokter: DokterModel){
        dokterRepository.updateDokter(dokter)
    }

    fun observeDokter() = dokterRepository.liveDokter

}