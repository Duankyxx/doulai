const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  publicPath: './',
  devServer: {
    host: 'localhost',
    port: 3000,
    open: true,
    // proxy:{
    //   //带有/api的请求就发给后端接口
    //   '/api':{
    //     //后端接口
    //     target:'http://127.0.0.1:8090'
    //   }
    // }
  }
})
