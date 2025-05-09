<template>
  <a-modal v-model="show" title="违规处理" @cancel="onClose" :width="900">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
      <a-button key="back1" @click="submit" type="primary">
        处理
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="userData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>违规编号：</b>
          {{ userData.code ? userData.code : '- -' }}
        </a-col>
        <a-col :span="8"><b>车牌号：</b>
          {{ userData.vehicleNo ? userData.vehicleNo : '- -' }}
        </a-col>
        <a-col :span="8"><b>车辆编号：</b>
          {{ userData.vehicleNumber ? userData.vehicleNumber : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>用户编号：</b>
          {{ userData.userCode ? userData.userCode : '- -' }}
        </a-col>
        <a-col :span="8"><b>用户名称：</b>
          {{ userData.name ? userData.name : '- -' }}
        </a-col>
        <a-col :span="8"><b>联系方式：</b>
          {{ userData.phone ? userData.phone : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>创建时间：</b>
          {{ userData.createDate ? userData.createDate : '- -' }}
        </a-col>
        <a-col :span="8"><b>处理状态：</b>
          <a-tag v-if="userData.status == 0" color="#f50">未处理</a-tag>
          <a-tag v-if="userData.status == 1" color="#87d068">已处理</a-tag>
        </a-col>
      </a-row>
      <br/>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="24"><b>违规内容：</b>
          {{ userData.content ? userData.content : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="24"><b>处理内容：</b>
          {{ userData.fixContent ? userData.fixContent : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">违规图片</span>
        </a-col>
        <a-col :span="24">
          <a-upload
            name="avatar"
            action="http://127.0.0.1:9527/file/fileUpload/"
            list-type="picture-card"
            :file-list="fileList"
            @preview="handlePreview"
            @change="picHandleChange"
          >
          </a-upload>
          <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
            <img alt="example" style="width: 100%" :src="previewImage"/>
          </a-modal>
        </a-col>
        <br/>
        <br/>
        <a-col :span="24">
          <a-textarea :rows="6" v-model="content" placeholder="处理内容"/>
        </a-col>
      </a-row>
      <br/>
    </div>
  </a-modal>
</template>

<script>
import baiduMap from '@/utils/map/baiduMap'
import moment from 'moment'

moment.locale('zh-cn')

function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}

export default {
  name: 'userView',
  props: {
    userShow: {
      type: Boolean,
      default: false
    },
    userData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.userShow
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      content: '',
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      repairInfo: null,
      durgList: [],
      logisticsList: [],
      userInfo: null
    }
  },
  watch: {
    userShow: function (value) {
      if (value) {
        if (this.userData.images) {
          this.imagesInit(this.userData.images)
        }
      }
    }
  },
  methods: {
    local (userData) {
      baiduMap.clearOverlays()
      baiduMap.rMap().enableScrollWheelZoom(true)
      // eslint-disable-next-line no-undef
      let point = new BMap.Point(userData.longitude, userData.latitude)
      baiduMap.pointAdd(point)
      baiduMap.findPoint(point, 16)
      // let driving = new BMap.DrivingRoute(baiduMap.rMap(), {renderOptions:{map: baiduMap.rMap(), autoViewport: true}});
      // driving.search(new BMap.Point(this.nowPoint.lng,this.nowPoint.lat), new BMap.Point(scenic.point.split(",")[0],scenic.point.split(",")[1]));
    },
    imagesInit (images) {
      if (images !== null && images !== '') {
        let imageList = []
        images.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileList = imageList
      }
    },
    handleCancel () {
      this.previewVisible = false
    },
    async handlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
    },
    picHandleChange ({fileList}) {
      this.fileList = fileList
    },
    submit () {
      if (!this.content) {
        this.$message.error('请填写处理内容')
        return false
      }
      this.userData.fixContent = this.content
      this.userData.status = 1
      this.$put('/cos/violation-info', {
        ...this.userData
      }).then((r) => {
        this.content = ''
        this.$emit('success')
      })
    },
    onClose () {
      this.content = ''
      this.$emit('close')
    }
  }
}
</script>

<style scoped>

</style>
