var util = require('../../utils/util.js');
var api = require('../../config/api.js');
import Dialog from '../../lib/vant-weapp/dialog/dialog';

var app = getApp();
Page({
  data: {
    status: false,
    orderId: 0
  },
  onLoad: function(options) {
    // 页面初始化 options为页面跳转所带来的参数
    this.setData({
      orderId: options.orderId,
      status: options.status === '1' ? true : false
    })
  },
  onReady: function() {

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
  trySubmitOrder: function() {
    const that = this;
    Dialog.confirm({
      title: '请确认付款',
      // message: '弹窗内容',
      asyncClose: true
    })
      .then(() => {
        setTimeout(() => {
          // 调用提交订单方法
          that.payOrder();
          Dialog.close();
        }, 1000);
      })
      .catch(() => {
        Dialog.close();
      })
  },
  payOrder() {
    let that = this;
    util.request(api.OrderPrepayTest, {
      orderId: that.data.orderId
    }, 'POST').then(function(res) {
      if (res.errno === 0) {
        const payParam = res.data;
        console.log("支付过程开始")
        
        wx.request(api.OrderPayTest, {
          orderId:that.data.orderId
        }, 'POST').then(function(res) {
          that.setData({
            status: true
          })
        
          
          // 跳转至付款结果页面
          // wx.redirectTo({
          //   url: '/pages/payResult/payResult?status=1&orderId=' + that.data.orderId
          // });
        })
        
        // wx.requestPayment({
        //   'timeStamp': payParam.timeStamp,
        //   'nonceStr': payParam.nonceStr,
        //   'package': payParam.packageValue,
        //   'signType': payParam.signType,
        //   'paySign': payParam.paySign,
        //   'success': function(res) {
        //     console.log("支付过程成功")
        //     that.setData({
        //       status: true
        //     });
        //   },
        //   'fail': function(res) {
        //     console.log("支付过程失败")
        //     util.showErrorToast('支付失败');
        //   },
        //   'complete': function(res) {
        //     console.log("支付过程结束")
        //   }
        // });
      }
    });
  }
})