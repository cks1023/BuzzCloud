<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>BuzzCloud</title>
		
		
    <script type="text/javascript" charset="utf-8" src="js/jquery-2.2.3.js"></script>
    <script type="text/javascript" charset="utf-8" src="oauth/jquery.cookie.js"></script>
    <script type="text/javascript" charset="utf-8" src="oauth/naverLogin.js"></script>



<script type="text/javascript">
    function generateState() {
       // CSRF 방지를 위한 state token 생성 코드
       // state token은 추후 검증을 위해 세션에 저장 되어야 합니다.
       var oDate = new Date();
		        return oDate.getTime();
		    }
    function saveState(state) {
		        $.removeCookie("state_token");
		        $.cookie("state_token", state);
		    }
    
    var naver = NaverAuthorize({
		        client_id : "Vf8cYbYQv2N0c_cSv_XA",
		        redirect_uri : "http://114.129.211.33:8181/main.do",
		        client_secret : "oPONcfR2BF"
		    });
			

     function loginNaver() {
 	   	
        var state = generateState();
        saveState(state);
     
        naver.login(state);
			    }
			
		    $("#NaverIdLoginBTN").click( function () {
		        var state = generateState();
		        saveState(state);
		        naver.login(state);
			    });
		    
		    // callback이 오면 checkLoginState()함수를 호출한다.
    		window.onload=function(){
						        checkLoginState();
			    }
		    
	     var tokenInfo = { access_token:"" , reㄴfresh_token:"" };
			    
			    function checkLoginState() {
				    var state = $.cookie("state_token");
				  
				    if(naver.checkAuthorizeState(state) === "connected") {
							        //정상적으로 Callback정보가 전달되었을 경우 Access Token발급 요청 수행
				       naver.getAccessToken(function(data) {
					
				           var response = data._response.responseJSON;
				           if(response.error === "fail") {
				               //access token 생성 요청이 실패하였을 경우에 대한 처리
				               return ;
				       					    }
				           tokenInfo.access_token = response.access_token;
				           tokenInfo.refresh_token = response.refresh_token;
				
				           //sonsole.log에 나온다.
				           console.log("success to get access token", response);
							        });
				    } else {
				        //Callback으로 전달된 데이터가 정상적이지 않을 경우에 대한 처리
				        return ;
				 		   }
			}
			</script>


			<style>
		
				input.NAVER_BUTTON{
			 border-top: 1px solid #96d1f8;
		   background: #40ff00;
		   background: -webkit-gradient(linear, left top, left bottom, from(#00ff2f), to(#40ff00));
		   background: -webkit-linear-gradient(top, #00ff2f, #40ff00);
		   background: -moz-linear-gradient(top, #00ff2f, #40ff00);
		   background: -ms-linear-gradient(top, #00ff2f, #40ff00);
		   background: -o-linear-gradient(top, #00ff2f, #40ff00);
		   padding: 7.5px 15px;
		   -webkit-border-radius: 4px;
		   -moz-border-radius: 4px;
		   border-radius: 4px;
		   -webkit-box-shadow: rgba(0,0,0,1) 0 1px 0;
		   -moz-box-shadow: rgba(0,0,0,1) 0 1px 0;
		   box-shadow: rgba(0,0,0,1) 0 1px 0;
		   text-shadow: rgba(0,0,0,.4) 0 1px 0;
		   color: white;
		   font-size: 15px;
		   font-family: Georgia, serif;
		   text-decoration: none;
		   vertical-align: middle;
		   }
input.button:hover {
   border-top-color: #68c41d;
   background: #68c41d;
   color: #ccc;
   }
input.button:active {
   border-top-color: #3ac43f;
   background: #3ac43f;
   }
			}
		
			</style>
		
		</head>
	<body>
		<form>
		 <input type="BUTTON" value="네이버로 로그인" name="LoginToNaver" class="NAVER_BUTTON"  onclick='loginNaver()'></input>
		</form>
	</body>
</html>