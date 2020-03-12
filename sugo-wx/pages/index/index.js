const util = require('../../utils/util.js');
const api = require('../../config/api.js');
const user = require('../../utils/user.js');

//获取应用实例
const app = getApp();

Page({
  data: {
    newGoods: [],
    hotGoods: [],
    topics: [],
    brands: [],
    groupons: [],
    floorGoods: [],
    banner: [],
    channel: [],
    coupon: [],
    goodsCount: 0,
    // 给定默认当前位置
    currentCity: '广东省 广州市 白云区'
  },

  onShareAppMessage: function() {
    return {
      title: '速购微信小程序商城',
      desc: '速购微信小程序商城',
      path: '/pages/index/index'
    }
  },

  onPullDownRefresh() {
    wx.showNavigationBarLoading() //在标题栏中显示加载
    this.getIndexData();
    wx.hideNavigationBarLoading() //完成停止加载
    wx.stopPullDownRefresh() //停止下拉刷新
  },

  getIndexData: function() {
    let that = this;
    util.request(api.IndexUrl).then(function(res) {
      if (res.errno === 0) {
        that.setData({
          newGoods: res.data.newGoodsList,
          hotGoods: res.data.hotGoodsList,
          topics: res.data.topicList,
          brands: res.data.brandList,
          floorGoods: res.data.floorGoodsList,
          banner: res.data.banner,
          groupons: res.data.grouponList,
          channel: res.data.channel,
          coupon: res.data.couponList
        });
      }
    });
    util.request(api.GoodsCount).then(function (res) {
      that.setData({
        goodsCount: res.data
      });
    });
  },
  onLoad: function(options) {

    // 页面初始化 options为页面跳转所带来的参数
    if (options.scene) {
      //这个scene的值存在则证明首页的开启来源于朋友圈分享的图,同时可以通过获取到的goodId的值跳转导航到对应的详情页
      var scene = decodeURIComponent(options.scene);
      console.log("scene:" + scene);

      let info_arr = [];
      info_arr = scene.split(',');
      let _type = info_arr[0];
      let id = info_arr[1];

      if (_type == 'goods') {
        wx.navigateTo({
          url: '../goods/goods?id=' + id
        });
      } else if (_type == 'groupon') {
        wx.navigateTo({
          url: '../goods/goods?grouponId=' + id
        });
      } else {
        wx.navigateTo({
          url: '../index/index'
        });
      }
    }

    // 页面初始化 options为页面跳转所带来的参数
    if (options.grouponId) {
      //这个pageId的值存在则证明首页的开启来源于用户点击来首页,同时可以通过获取到的pageId的值跳转导航到对应的详情页
      wx.navigateTo({
        url: '../goods/goods?grouponId=' + options.grouponId
      });
    }

    // 页面初始化 options为页面跳转所带来的参数
    if (options.goodId) {
      //这个goodId的值存在则证明首页的开启来源于分享,同时可以通过获取到的goodId的值跳转导航到对应的详情页
      wx.navigateTo({
        url: '../goods/goods?id=' + options.goodId
      });
    }

    // 页面初始化 options为页面跳转所带来的参数
    if (options.orderId) {
      //这个orderId的值存在则证明首页的开启来源于订单模版通知,同时可以通过获取到的pageId的值跳转导航到对应的详情页
      wx.navigateTo({
        url: '../ucenter/orderDetail/orderDetail?id=' + options.orderId
      });
    }

    this.getIndexData();
    this.toStorage();
  },
  onReady: function() {
    // 页面渲染完成
  },
  onShow: function() {
    // 页面显示
  },
  onHide: function() {
    // 页面隐藏
  },
  onUnload: function() {
    // 页面关闭
  },
  getCoupon(e) {
    if (!app.globalData.hasLogin) {
      wx.navigateTo({
        url: "/pages/auth/login/login"
      });
    }

    let couponId = e.currentTarget.dataset.index
    util.request(api.CouponReceive, {
      couponId: couponId
    }, 'POST').then(res => {
      if (res.errno === 0) {
        wx.showToast({
          title: "领取成功"
        })
      }
      else{
        util.showErrorToast(res.errmsg);
      }
    })
  },

  // 重新获取定位
  getNewLocation: function() {
    wx.showLoading({
      title: '获取定位中...',
    })
    let that = this;
    try {
      wx.removeStorageSync('currentCity')
    } catch (e) {

    }
    wx.getLocation({
      type: 'wgs84',
      success: function(res) {
        var longitude = res.longitude
        var latitude = res.latitude
        that.loadCity(longitude, latitude)
      }
    })
    wx.hideLoading({
      complete: (res) => {
        wx.showToast({
          title: '定位成功',
        })
      },
    })
  },

  getLocation: function() {
    
    console.log('点击了1次')
    var page = this;
    let that = this;
    wx.getSetting({
      success(res) {
        if(!res.authSetting['scope.userLocation']) {
          wx.authorize({
            scope: 'scope.userLocation',
            success() {
              wx.getLocation({
                type: 'wgs84',
                success: function(res) {
                  var longitude = res.longitude
                  var latitude = res.latitude
                  that.loadCity(longitude, latitude)
                }
              })
              console.log("用户已经同意位置授权");
            },
            fail() {
              console.log("用户已经拒绝位置授权");
              that.openConfirm();
            }
            
          })
        }
      }
    })
    // wx.getLocation({
    //   type: 'wgs84',
    //   success: function(res) {
    //     var longitude = res.longitude
    //     var latitude = res.latitude
    //     page.loadCity(longitude, latitude)
    //   }
    // })
  },
  loadCity: function(longitude, latitude) {
    wx.showLoading({
      title: '获取定位中...',
    })
    var page = this
    var ak = '6WTg9vFdq7nDcNPh3vEhvwklRjcpy7gh'
    wx.request({
      
      url: 'http://api.map.baidu.com/reverse_geocoding/v3/?ak='+ak+'&output=json&coordtype=wgs84ll&location='+latitude+','+longitude,
      data: {},
      header: {
        'Content-Type': 'application/json'
      },
      success: function(res) {
        console.log(res);
        var province = res.data.result.addressComponent.province;
        var city = res.data.result.addressComponent.city;
        var district = res.data.result.addressComponent.district;
        var newCity = province + ' ' + city + ' ' + district;
        page.setData(
          {currentCity: newCity}
        );
        wx.showToast({
          title: '定位成功',
        })
        try {
          console.log('缓存数据：' + newCity)
          wx.setStorageSync('currentCity', newCity)
        } catch(e) {
          console.log('缓存失败')
        }
      },
      fail: function() {
        that.setData({
          currentCity: '广东省 广州市 白云区'
        })
        util.showErrorToast('定位当前位置失败，使用默认值！');
      }
    });
    wx.hideLoading();
  },
  openConfirm: function() {
    let that = this;
    wx.showModal({
      content: '检测到您没打开此小程序的定位权限，是否去设置打开？',
      confirmText: "确认",
      cancelText: "取消",
      success: function (res) {
        console.log(res);
        //点击“确认”时打开设置页面
        if (res.confirm) {
          console.log('用户点击确认')
          wx.openSetting({
            success: (res) => { 
              wx.getLocation({
                  type: 'wgs84',
                  success: function(res) {
                    var longitude = res.longitude
                    var latitude = res.latitude
                    that.loadCity(longitude, latitude)
                  }
                })
            }
          })
        } else {
          console.log('用户点击取消')
        }
      }
    });
  },
  // 获取缓存数据，没有缓存就尝试让用户获取地理位置
  toStorage: function() {
    let that = this;
    try {
      var value = wx.getStorageSync('currentCity')
      if(value) {
        console.log('获取到缓存' + value)
        that.setData({
          currentCity: value
        })
      } else {
        console.log('没有缓存')
        that.getLocation();
      }
    } catch (e) {
      console.log(e)
    }
  }

})