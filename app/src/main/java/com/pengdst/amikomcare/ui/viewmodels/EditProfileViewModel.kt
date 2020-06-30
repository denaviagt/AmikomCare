package com.pengdst.amikomcare.ui.viewmodels

import com.pengdst.amikomcare.datas.models.DokterModel

class EditProfileViewModel : BaseMainViewModel(){

    fun updateDokter(dokter: DokterModel){
        this.dokterRepository.updateDokter(dokter)
    }

    fun observeDokter() = this.dokterRepository.liveDokter

}