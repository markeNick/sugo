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
    currentCity: '广东省 广州市 白云区',
    adcode: '440111',   // 行政编码
    currentPage: 1,     // 当前页码
    noMoreGoods: false, // 是否没有商品数据了， true为数据加载完毕
    limit: 10
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
    this.setData({
      currentPage: 1,
      noMoreGoods: false,
      newGoods: []
    })
    this.getIndexData();
    wx.hideNavigationBarLoading() //完成停止加载
    wx.stopPullDownRefresh() //停止下拉刷新
  },

  getIndexData: function() {
    let that = this;
    util.request(api.IndexUrl, {adcode: that.data.adcode}).then(function(res) {
      if (res.errno === 0) {
        that.setData({
          newGoods: res.data.newGoodsList,
          brands: res.data.brandList,
          banner: res.data.banner,
          channel: res.data.channel,
        });
      }
    });
    that.getGoodsCount();
    console.log('noMoreGoods:'+that.data.noMoreGoods)
    console.log(that.data.newGoods.length)
  },
  getGoodsCount: function() {
    let that = this;
    util.request(api.GoodsCount, {
      adcode: that.data.adcode
    }).then(function (res) {
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
    this.toStorage();
    this.getIndexData();
    
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
  onReachBottom: function() {
    let that = this;
    console.log('noMoreGoods:'+that.data.noMoreGoods)
    console.log(that.data.newGoods.length)
    var page = that.data.currentPage;
    if(!that.data.noMoreGoods) {
      page++
      that.setData({
        currentPage: page
      })
      that.getGoodsList();
    }
  },

  getGoodsList:function() {
    let that = this;
    
    util.request(api.NewGoodsList, {
      adcode: that.data.adcode,
      currentPage: that.data.currentPage
    }).then(function(res) {
      if (res.errno === 0) {
        var allArr = [];
        var newArr = res.data.newGoodsList;
        var initArr = that.data.newGoods ? that.data.newGoods : []; // 获取已经加载的商品
        var lastPageLength = initArr.length;
        if(that.data.currentPage <= 1) {  // 如果是第一页
          allArr = res.data.newGoodsList;
        } else {                // 如果不是第一页，连接已经加载与新加载的商品
          allArr = initArr.concat(newArr);
        }
        if(allArr.length >= that.data.goodsCount) {
          that.setData({
            noMoreGoods: true
          })
        }
        console.log(allArr)
        that.setData({
          newGoods: allArr,
        })
      }
    });
  },
  // 重新获取定位
  getNewLocation: function() {
    let that = this
    try {
      wx.removeStorageSync('currentCity')
      that.getLocation();
      
    } catch (e) {

    }
  },
  getLocation: function() {
    let that = this
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
        } else {
          wx.getLocation({
            type: 'wgs84',
            success: function(res) {
              console.log("用户已经授权过了")
              var longitude = res.longitude
              var latitude = res.latitude
              that.loadCity(longitude, latitude)
            }
          })
        }
      }
    })
    that.getIndexData();
  },
  openConfirm: function() {
    let that = this;
    wx.showModal({
      content: '检测到您没打开此小程序的定位权限，是否去设置打开？',
      confirmText: "确认",
      cancelText: "取消",
      success: function (res) {
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
  loadCity: function(longitude, latitude) {
    wx.showLoading({
      title: '获取定位中...'
    })
    var that = this
    var ak = '6WTg9vFdq7nDcNPh3vEhvwklRjcpy7gh'
    wx.request({

      url: 'http://api.map.baidu.com/reverse_geocoding/v3/?ak='+ak+'&output=json&coordtype=wgs84ll&location='+latitude+','+longitude,
      data: {},
      header: {
        'Content-Type': 'application/json'
      },
      success: function(res) {
        console.log(res);
        var newAdCode = res.data.result.addressComponent.adcode;
        var province = res.data.result.addressComponent.province;
        var city = res.data.result.addressComponent.city;
        var district = res.data.result.addressComponent.district;
        var newCity = province + ' ' + city + ' ' + district;
        
        that.setData({
          currentCity: newCity,
          adcode: newAdCode,
          currentPage: 1
        });
        console.log('currentPage:'+ that.data.currentPage)
        wx.hideLoading({
          complete: (res) => {
            wx.showToast({
              title: '定位成功',
            })
          },
        })

        that.getIndexData()
        that.getGoodsCount();

        try {
          console.log('缓存数据：' + newCity + ' ' + newAdCode)
          wx.setStorageSync('currentCity', newCity)
          wx.setStorageSync('adcode', newAdCode)
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
    })
  },
  
  // 获取缓存数据，没有缓存就尝试让用户获取地理位置
  toStorage: function() {
    let that = this;
    try {
      var city = wx.getStorageSync('currentCity')
      var code = wx.getStorageSync('adcode')
      if(city) {
        console.log('获取到缓存' + city)
        that.setData({
          currentCity: city,
          adcode: code
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