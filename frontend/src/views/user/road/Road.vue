<template>
  <a-drawer
    placement="right"
    width="100%"
    :closable="false"
    :visible="visible"
    @close="handleOk"
    wrapClassName="aa"
    :getContainer="false"
  >
    <div style="width: 100%">
      <a-icon type="arrow-left" style="position: absolute;z-index: 999;color: red;font-size: 20px;margin: 15px" @click="home"/>
      <a-row style="height:100vh;font-family: SimHei">
        <a-col :span="16" style="height: 100%;">
          <div id="areas" style="width: 100%;height: 100vh;box-shadow: 3px 3px 3px rgba(0, 0, 0, .2);background:#ec9e3c;color:#fff"></div>
        </a-col>
        <a-col :span="8" style="height: 100vh;color:#fff;overflow-y: auto">
          <div style="padding: 20px;margin: 0 auto">
            <a-row :gutter="15" style="width: 100%">
              <a-col :span="12">
                <a-input-search v-model="startPoint.address" placeholder="选择起点" style="margin-top: 20px;width: 100%" @search="onSearch0"/>
              </a-col>
              <a-col :span="12">
                <a-input-search v-model="endPoint.address" placeholder="选择终点" style="margin-top: 20px;width: 100%" @search="onSearch1"/>
              </a-col>
              <a-col :span="24">
                <a-button style="margin-top: 20px;width: 100%" @click="navigation()" type="primary">导航</a-button>
              </a-col>
            </a-row>
            <a-row :gutter="15" style="width: 100%">
              <a-col :span="12">
                <div v-show="cardShow" style="margin-top: 20px">
                  <a-card hoverable style="width: 100%;margin-bottom: 15px" v-for="local in localData" :key="local.uid" @click="checkPoint(local)">
                    <template class="ant-card-actions" slot="actions">
                    </template>
                    <a-card-meta :title="local.title" :description="local.address"></a-card-meta>
                  </a-card>
                </div>
              </a-col>
              <a-col :span="24">
                <div style="font-size: 12px;font-family: SimHei;margin-top: 20px" v-if="roadData.length !== 0">
                  <a-row>
                    <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">道路导航</span></a-col>
                    <a-col :span="24" style="height: 100%;overflow-y: auto">
                      <a-skeleton active v-if="loading" />
                      <a-timeline v-if="!loading">
                        <a-timeline-item v-for="(item,index) in roadData" :key="index">
                          <div v-html="item"></div>
                        </a-timeline-item>
                      </a-timeline>
                    </a-col>
                  </a-row>
                  <br/>
                </div>
              </a-col>
            </a-row>
          </div>
        </a-col>
      </a-row>
    </div>
  </a-drawer>
</template>

<script>
import baiduMap from '@/utils/map/baiduMap'

export default {
  name: 'drawerMap',
  data () {
    return {
      visible: false,
      mapId: 'areass',
      cardShow: false,
      loading: false,
      startPoint: {
        lng: null,
        lat: null,
        address: ''
      },
      endPoint: {
        lng: null,
        lat: null,
        address: ''
      },
      local: '',
      roadData: [],
      localData: [],
      checkType: 0
    }
  },
  props: {
    childrenDrawerShow: {default: false}
  },
  watch: {
    childrenDrawerShow: function (newval, oldval) {
      if (newval) {
        console.log(newval)
        setTimeout(function () {
          baiduMap.initMap('areass')
        }, 500)
        // if(Object.keys(baiduMap.rMap()).length === 0) {
        //
        // }
      }
    }
  },
  mounted () {
    this.visible = true
    setTimeout(() => {
      baiduMap.initMap('areas')
      this.getLocal()
    }, 500)
  },
  methods: {
    checkPoint (data) {
      if (this.checkType == 0) {
        this.startPoint.lng = data.point.lng
        this.startPoint.lat = data.point.lat
      }
      if (this.checkType == 1) {
        this.endPoint.lng = data.point.lng
        this.endPoint.lat = data.point.lat
      }
      this.cardShow = false
      baiduMap.clearOverlays()
    },
    navigation (data) {
      if (this.startPoint.lng && this.endPoint.lng && this.startPoint.lat && this.endPoint.lat) {
        baiduMap.clearOverlays()
        baiduMap.rMap().enableScrollWheelZoom(true)
        // eslint-disable-next-line no-undef
        let driving = new BMap.DrivingRoute(baiduMap.rMap(), {renderOptions: {map: baiduMap.rMap(), autoViewport: true}})
        // eslint-disable-next-line no-undef
        driving.search(new BMap.Point(this.startPoint.lng, this.startPoint.lat), new BMap.Point(this.endPoint.lng, this.endPoint.lat))
        this.getRoadData(data)
      } else {
        this.$message.error('请选择起点和终点')
      }
    },
    getRoadData (data) {
      let options = {
        onSearchComplete: results => {
          // eslint-disable-next-line eqeqeq,no-undef
          if (driving.getStatus() == BMAP_STATUS_SUCCESS) {
            // 获取第一条方案
            let plan = results.getPlan(0)
            // 获取方案的驾车线路
            // eslint-disable-next-line no-unused-vars
            let route = plan.getRoute(0)
            // 获取每个关键步骤,并输出到页面
            let s = []
            for (let j = 0; j < plan.getNumRoutes(); j++) {
              let route = plan.getRoute(j)
              for (let i = 0; i < route.getNumSteps(); i++) {
                let step = route.getStep(i)
                s.push((i + 1) + '. ' + step.getDescription())
              }
            }
            this.roadData = s
          }
        }
      }
      // eslint-disable-next-line no-undef
      let driving = new BMap.DrivingRoute(baiduMap.rMap(), options)
      // eslint-disable-next-line no-undef
      let start = new BMap.Point(this.startPoint.lng, this.startPoint.lat)
      let end = new BMap.Point(this.endPoint.lng, this.endPoint.lat)

      // eslint-disable-next-line no-undef
      driving.search(start, end)
    },
    home () {
      this.$router.push('/home')
    },
    getLocal () {
      // eslint-disable-next-line no-undef
      let geolocation = new BMap.Geolocation()
      geolocation.getCurrentPosition(r => {
        this.nowPoint = r.point
      }, {enableHighAccuracy: true})
    },
    handleOk (e) {
      this.visible = false
    },
    onChildrenDrawerClose () {
      this.handlerClosed()
    },
    handlerClosed (localPoint) {
      this.$emit('handlerClosed', localPoint)
    },
    addPoint (point) {
      this.localPoint = point
    },
    onSearch0 () {
      this.checkType = 0
      let localData = []
      var options = {
        onSearchComplete: (results) => {
          // 判断状态是否正确
          // eslint-disable-next-line no-undef,eqeqeq
          if (local.getStatus() == BMAP_STATUS_SUCCESS) {
            for (var i = 0; i < results.getCurrentNumPois(); i++) {
              if (i === 0) {
                setTimeout(() => {
                  baiduMap.findPoint(results.getPoi(0).point, 15)
                }, 10)
              }
              localData.push(results.getPoi(i))
              if (results.getPoi(i).point !== undefined) {
                baiduMap.localPointAdd(results.getPoi(i))
              }
            }
            this.localData = localData
            this.cardShow = true
          }
        }
      }
      // eslint-disable-next-line no-undef
      var local = new BMap.LocalSearch(baiduMap.rMap(), options)
      local.search(this.startPoint.address)
    },
    onSearch1 () {
      this.checkType = 1
      let localData = []
      var options = {
        onSearchComplete: (results) => {
          // 判断状态是否正确
          // eslint-disable-next-line no-undef,eqeqeq
          if (local.getStatus() == BMAP_STATUS_SUCCESS) {
            for (var i = 0; i < results.getCurrentNumPois(); i++) {
              if (i === 0) {
                setTimeout(() => {
                  baiduMap.findPoint(results.getPoi(0).point, 15)
                }, 10)
              }
              localData.push(results.getPoi(i))
              if (results.getPoi(i).point !== undefined) {
                baiduMap.localPointAdd(results.getPoi(i))
              }
            }
            this.localData = localData
            this.cardShow = true
          }
        }
      }
      // eslint-disable-next-line no-undef
      var local = new BMap.LocalSearch(baiduMap.rMap(), options)
      local.search(this.endPoint.address)
    }
  }
}
</script>

<style scoped>

>>> .ant-drawer-body {
  padding: 0 !important;
}
>>> .ant-card-meta-title {
  font-size: 13px;
  font-family: SimHei;
}
>>> .ant-card-meta-description {
  font-size: 12px;
  font-family: SimHei;
}
>>> .ant-divider-with-text-left {
  margin: 0;
}

>>> .ant-card-head-title {
  font-size: 13px;
  font-family: SimHei;
}
>>> .ant-card-extra {
  font-size: 13px;
  font-family: SimHei;
}
>>> .ant-radio-button-wrapper {
  border-radius: 0;
}
</style>
