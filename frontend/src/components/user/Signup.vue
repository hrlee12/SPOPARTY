<template>
  <v-container class="signup-container">
    <v-card class="signup-card mx-auto" outlined>
      <v-card-title class="title justify-center"><h2>회원 가입</h2></v-card-title>
      <v-card-text>
        <v-form @submit.prevent="submitSignup">
          <v-row>
            <v-col cols="9">
              <v-text-field
                class="input"
                label="아이디"
                v-model="id"
                outlined
                required
              ></v-text-field>
            </v-col>
            <v-col cols="3">
              <v-btn
                class="id-check"
                color="#333D51"
                elevation="1"
                @click="checkId"
              >
                아이디 중복확인
              </v-btn>
            </v-col>
          </v-row>
          
          <v-text-field
            class="input"
            label="닉네임"
            v-model="nickname"
            outlined
            required
          ></v-text-field>
          
          <v-text-field
            class="input" 
            label="비밀번호"
            v-model="password"
            :type="'password'"
            outlined
            required
          ></v-text-field>
          
          <v-text-field
            class="input"
            label="비밀번호 확인"
            v-model="password2"
            :type="'password'"
            outlined
            required
          ></v-text-field>
          
          <v-row align="center">
            <v-col cols="12" md="4" sm="4" xs="4">
              <v-text-field
                class="input"
                label="이메일"
                v-model="emailId"
                outlined
                required
                dense
              ></v-text-field>
            </v-col>
            <v-col cols="12" md="1" sm="1" xs="1" class="text-center" style="color:white"><h2>@</h2></v-col>
            <v-col cols="12" md="4" sm="4" xs="4">
              <v-text-field
                class="input"
                label="도메인"
                v-model="emailDomain"
                outlined
                required
                dense
              ></v-text-field>
            </v-col>
            <v-col cols="12" md="3" sm="3" xs="3">
              <v-btn class="email-verify"
                color="#333D51"
                elevation="1"
                @click="showEmailVerify"
                >이메일 인증</v-btn>
              <!-- <EmailVerify v-if="isEmailVerifyVisible" @close="isEmailVerifyVisible=false"/> -->

            </v-col>
          </v-row>

        <v-row v-if="emblem.emblemId !== ''" justify="center" align="center" class="emblem mx-2" @click="showEmblemModal">
          <v-col cols="auto" class="pa-0">
            <v-img :src="emblem.emblemIcon" :alt="emblem.emblemName" class="mx-2" style="width: 50px; height: 50px; cursor:pointer"/>
          </v-col>
          <v-col cols="auto" class="pa-0">
            <h2 style="color:white; cursor:pointer">{{ emblem.emblemName }}</h2>
          </v-col>
        </v-row>
        <v-row v-else justify="center" align="center"  class="emblem mx-2 my-2" @click="showEmblemModal">
          <v-tooltip location="top" activator="parent">대표 엠블렘을 선택하세요!</v-tooltip>
          <h2 style="color:white; cursor:pointer">원하는 구단의 엠블럼을 선택해보세요!</h2>
        </v-row>

        <EmblemList 
          v-if="isEmblemModalVisible"
          :team-list="teamList"
          @emblem-list-close="isEmblemModalVisible = false"
          @select-emblem="setEmblem($event)"
        />
          
          <v-row>
            <v-col cols=3>
              <v-btn color="warning" @click="goBack" block>이전</v-btn>
            </v-col>
            <v-spacer></v-spacer>
            <v-col cols="5">
              <v-btn color="primary" type="submit" block>회원가입</v-btn>
            </v-col>
          </v-row>
          
        </v-form>
      </v-card-text>
    </v-card>
  </v-container>
  
  <!-- ******************************************** -->
  <!-- ***********여기서부터 모달*********** -->
  <!-- 아이디를 안 적은 상태에서 아이디 중복검사를 한 경우 -->
  <v-dialog
    v-model="blankId"
    class="idCheckModal"
    persistent
  >
    <v-card class="modals">
      <v-card-text>
        아이디를 입력하세요!
      </v-card-text>
      <v-card-actions class="justify-center">
        <v-btn color="red" @click="blankId = false"> 확인</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>

  <!-- 사용 가능한 아이디인 경우 -->
  <v-dialog
    v-model="validId"
    class="idCheckModal"
    persistent
  >
    <v-card class="modals">
      <v-card-text>
        사용 가능한 아이디입니다.
      </v-card-text>
      <v-card-actions class="justify-center">
        <v-btn class="button" @click="validId = false">확인</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>

  <!-- 중복된 아이디인 경우 -->
  <v-dialog
    v-model="inValidId"
    class="idCheckModal"
    persistent
  >
    <v-card class="modals">
      <v-card-text>
        중복된 아이디입니다!
      </v-card-text>
      <v-card-actions class="justify-center">
        <v-btn color="red darken-1" @click="inValidId = false">확인</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
    
  <!-- 이메일 인증 모달 -->
  <v-dialog
    v-model="isEmailVerifyVisible"
    width="400"
    persistent
  >
    <v-card class="modals justify-center">
      <v-card-title class="text-h5">이메일 인증</v-card-title>
      <v-card-text>
        <p>인증 코드가 이메일 주소로 전송되었습니다.</p>
        <p> 코드를 입력해 주세요.</p>
        <v-text-field
          solo
          placeholder="인증 코드 입력"
          type="text"
          v-model="verifyCode"
        ></v-text-field>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn class="button"  @click="checkVerifyCode">인증</v-btn>
        <v-btn color="grey" @click="isEmailVerifyVisible = false">취소</v-btn>
      </v-card-actions>
    </v-card>

  </v-dialog>

</template>
  
  <script setup>
  import {useRouter} from 'vue-router';
  import { ref,computed, watch, onMounted } from 'vue';
  import {registMember} from '@/api/member'
  import {idCheck, emailCheck,verifyCodeCheck} from '@/api/authentication'
  import { httpStatusCode } from '@/util/http-status';
  import {useFollowStore} from '@/stores/member/follows'


  import EmblemList from '@/components/user/EmblemList.vue';
  
  const followStore = useFollowStore();

  const router = useRouter()

  const id = ref("");
  const nickname = ref("");
  const password = ref("");
  const password2 = ref("");
  const emailId = ref('');
  const emailDomain = ref('');
  const email = computed(() => {
    return `${emailId.value}@${emailDomain.value}`
  } )

  const teamList = ref([]);

  onMounted(() => {
    teamList.value = followStore.getTeamList();
  })

  watch(() => followStore.teamList, (newTeamList) => {
      teamList.value = newTeamList
  },{immediate:true})

  // 구단 엠블럼 고르기!
  const emblem = ref({
    emblemId : "",
    emblemIcon : "",
    emblemName : "",
  });

  const isEmblemModalVisible = ref(false) // 엠블럼 수정 모달 보일까 말까
  function showEmblemModal() {
      isEmblemModalVisible.value = true;
  }

  function setEmblem(newEmblem) {
    emblem.value = newEmblem
    // console.log("******내가 선택한 엠블럼********")
    // console.log(emblem.value)
  }


  // 아이디 중복 검사
  const idDuplicatedChecked = ref(false); // 아이디 검사 여부 flag
  const blankId = ref(false) // 아이디 칸이 공란인 경우
  const validId = ref(false); // 사용 가능한 아이디인 경우 모달 띄우기
  const inValidId = ref(false); // 중복된 아이디인 경우 모달 띄우기

  const checkId = () => {
    if(id.value === '') {
      blankId.value = true;
    }
    idDuplicatedChecked.value = false;
    // console.log("idDuplicatedChecked -> ",idDuplicatedChecked.value)
    idCheck(
      id.value,
      (res) => {
        // console.log(res);
        if (res.data.status === httpStatusCode.OK) {
          // console.log("히히 아이디 중복 검사 발사")
          idDuplicatedChecked.value = true; // 아이디 중복 검사 완료
          // console.log("idDuplicatedChecked -> ",idDuplicatedChecked.value)
          validId.value = true // 아이디 검사 완료 모달
        }
      },
      (error) => {
        // console.error("*******비상*******")
        if (error.response.status === httpStatusCode.CONFLICT) {
          // console.log("히히 이미 있는 아이디 발사")
          inValidId.value = true; // 아이디 중복 모달 
        }
        if (error.response.status === 500) {
          // console.log("중복체크를 하지 않음!!")
          console.error(error)
        }
      }
    )
  }

  // 만약 아이디 인증 후 다른 아이디로 바꾸면 다시 체크하도록 해야한다.
  watch(() => id.value,() => {
    idDuplicatedChecked.value = false;
  })

  // 이메일 인증 모달
  const isEmailVerifyVisible = ref(false);
  
  function showEmailVerify() {
    // 둘 중 하나라도 공란이거나 형식이 잘못되면 alert
    if(emailId.value === '' || emailDomain.value === '' || !isEmailValid(email.value)) {
      alert("올바른 이메일을 입력해주세요!")
      return;
    }
    // console.log("이메일 잘 왔나? -> ", email.value)

    // 이메일 ㄱㄱ
    emailCheck(
      email.value,
      (res) => {
        if(res.status === httpStatusCode.OK) {
          // console.log(res.status)
          isEmailVerifyVisible.value = true;
        }
      },
      (error) => {
        if (error.response.status === httpStatusCode.CONFLICT) {
          console.log(error)
          alert("이미 사용 중인 이메일입니다!")
        }
      }
    ) 
  }

  // 이메일 인증 코드 적합성 검사
  const verifyCode = ref('')
  const validEmail = ref(false); 
  const emailVerified = ref(false)

  function checkVerifyCode () {
    const data = {
      email:email.value,
      code:verifyCode.value
    }
    // console.log(data)
    verifyCodeCheck(
      data,
      (res)=>{
        if(res.status === httpStatusCode.OK){
          alert("알맞은 인증코드입니다!")
          validEmail.value = true
          emailVerified.value = true
          isEmailVerifyVisible.value = false;
        }
      },
      (error)=>{
        console.log(error)
        if (error.response.status === "400") {
          console.log(error)
          alert("인증번호가 다릅니다!")
        }
      }
    )

  }
  


  
  // 비밀번호 형식 검사
  const isPasswordValid = (pwd) => {
    return pwd.length >= 8 && /[!@#$%^&*(),.?":{}|<>]/g.test(pwd);
  };
  
  // 이메일 형식 검사
  const isEmailValid = (email) => {
  const re =
    /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
  };

  // 회원가입 최종
  function submitSignup() {
      if (
      id.value === "" ||
      password.value === "" ||
      nickname.value === "" ||
      email.value === "" ||
      emblem.value.emblemId === ""
      ) {
        alert("모든 내용을 입력해주세요");
        // console.log("id -> ",id.value)
        // console.log("password -> ",password.value)
        // console.log("nickname -> ",nickname.value)
        // console.log("email -> ",email.value)
        // console.log("emblem -> ",emblem.value.emblemId)
        return;
      }
    if (!idDuplicatedChecked.value){
      alert("아이디 중복 검사를 하지 않으셨습니다!")
      return;
    }

    if (password.value !== password2.value) {
        alert("비밀번호가 일치하지 않습니다.");
        return;
      }

    if (!isPasswordValid(password.value)) {
        alert("비밀번호는 8자리 이상이며, 특수문자를 포함해야 합니다.");
        return;
      }

    if (!isEmailValid(email.value)) {
      alert("올바른 이메일 형식을 입력해주세요.");
      return;
    }

    if (!emailVerified.value) {
      alert("이메일 인증 검사를 하지 않으셨습니다!");
      return;
    }

    if(emblem.value.emblemId === '') {
      // console.log(emblem.value.emblemId == '')
      alert("엠블럼은 반드시 골라야 합니다!!");
      return;
    }


    const member = {
      loginId : id.value,
      loginPwd : password.value,
      nickname : nickname.value,
      email : email.value,
      team : {
        id : emblem.value.emblemId
      }
    }

    registMember(member,
      (res) => {
        if (res.status === httpStatusCode.CREATE) {
          console.log(res);
          alert("축하합니다! SPOPARTY의 회원이 되셨습니다!")
          window.location.replace("/")
        } 
        else if (res.status === httpStatusCode.CONFLICT) {
          alert("이미 등록된 아이디입니다. ")
          return;
        }
      },
      (error) => {
        console.log(error)
        alert("히히 회원가입 실패 발사")
      }
    )
  }

  function goBack(){
    router.back(); // 이전 페이지로 이동
  }

 
  </script>
  
<style scoped lang="scss">
.emblem {
  background-color: #474F7A; 
  height:100px;
  border-radius: 20px;
}


.signup-container {
  max-width: 700px;
  background-color: #CBD0D8
}

.v-card {
  // background-color: #CBD0D8
  padding: 20px;
}

.input {
  background-color : #CBD0D8;
  height:60px;
  margin-top:10px;
  margin-bottom:10px;
  border-radius: 5px;
}

.title {
  margin-top:10px;
  margin-bottom : 10px;
  color : #292646;
}

.justify-center{
  text-align: center;
} 

.id-check{
  margin-top : 20px;
}

.idCheckModal {
  max-width:400px;
  text-align: center;
}

.modals {
  background-color:#CBD0D8
}

.email-verify{
  margin-bottom:3px;
}

</style>
