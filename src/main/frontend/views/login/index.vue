<template>
  <div class="login-body">
    <div class="loginWarp"
         v-loading="loading"
         element-loading-text="正在登陆中...">
      <div class="login-form">
        <el-form ref="form" :model="form" :rules="rules" label-width="0">
          <el-form-item prop="username" class="login-item">
            <el-input v-model="form.username" placeholder="请输入账户名：" class="form-input"></el-input>
          </el-form-item>
          <el-form-item prop="password" class="login-item">
            <el-input type="password" v-model="form.password" placeholder="请输入账户密码：" class="form-input"></el-input>
          </el-form-item>
          <el-form-item class="login-item">
            <el-button size="large" icon="check" class="form-submit" @click="submit">login</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>
<script type="text/javascript">
  import _ from '@/apis'

  export default {
    data () {
      return {
        form: {
          username: '',
          password: ''
        },
        rules: {
          username: [{required: true, message: '请输入账户名！', trigger: 'blur'}],
          password: [{required: true, message: '请输入账户密码！', trigger: 'blur'}]
        },
        // 请求时的loading效果
        loading: false
      }
    },
    methods: {
      submit () {
        console.log('login ...')
        let self = this
        self.$refs.form.validate((valid) => {
          if (valid) {
            this.loading = true
            _.login(this.form).then(data => {
              console.log(JSON.stringify(data))
              if (data.success) {
                self.$router.replace({path: '/'})
              } else {
                self.$message({
                  message: data.error,
                  type: 'error'
                })
              }
              self.loading = false
            })
          } else {
            return false
          }
        })
      }
    }
  }
</script>
<style lang="scss" type="text/css" rel="stylesheet/scss">
  .login-body {
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background-repeat: no-repeat;
    background-position: center;
    background-size: cover;
    .loginWarp {
      width: 300px;
      padding: 25px 15px;
      margin: 100px auto;
      background-color: #fff;
      border-radius: 5px;
      .login-title {
        margin-bottom: 25px;
        text-align: center;
      }
      .login-item {
        .el-input__inner {
          margin: 0 !important;
        }
      }
      .form-input {
        input {
          margin-bottom: 15px;
          font-size: 12px;
          height: 40px;
          border: 1px solid #eaeaec;
          background: #eaeaec;
          border-radius: 5px;
          color: #555;
        }
      }
      .form-submit {
        width: 100%;
        color: #fff;
        border-color: #6bc5a4;
        background: #6bc5a4;
        &:active, &:hover {
          border-color: #6bc5a4;
          background: #6bc5a4;
        }
      }
    }
  }
</style>
