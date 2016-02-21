﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using APIShopKaro.Models;
using APIShopKaro.Services;

using System.Web.Http.Description;

namespace APIShopKaro.Controllers
{
    [RoutePrefix("api/Users")]
    public class UserController : ApiController
    {
        /// <summary>
        /// Add new Seller/ Buyer
        /// </summary>
        /// <param name="user"></param>
        /// <returns></returns>
        [HttpPost]
        [Route("AddNewUser")]        
        public HttpResponseMessage AddNewUser(USER user)
        {
            try
            {
                var userService = new UserService();
                var id = userService.AddNewUser(user);
                var response = Request.CreateResponse(HttpStatusCode.OK, id);
                return response;
            }
            catch(Exception e)
            {
                var error = Request.CreateResponse(HttpStatusCode.InternalServerError, e.Message);
                return error;
            }
        }

        /// <summary>
        /// Get user by id
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        [HttpPost]
        [Route("GetUserById/{id}")]

        public HttpResponseMessage GetUserById(Guid? id)
        {
            try
            {
                var userService = new UserService();
                var user = userService.GetUserById(id);
                var response = Request.CreateResponse(HttpStatusCode.OK, user);
                return response;
            }
            catch (Exception e)
            {
                var error = Request.CreateResponse(HttpStatusCode.InternalServerError, e.Message);
                return error;
            }
        }

        /// <summary>
        /// Get user by id
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        [HttpPost]
        [Route("GetUserLogin")]
        [ResponseType(typeof(USER))]
        public HttpResponseMessage GetUserLogin(String UserEmail , String UserPassword)
        {
            try
            {
                var userService = new UserService();
                var user = userService.GetUserById(UserEmail);
                
                if (user != null && user.PASSWORD.Equals(UserPassword))
                {
                    var response = Request.CreateResponse(HttpStatusCode.OK, user);
                    return response;
                }
                var error = Request.CreateResponse(HttpStatusCode.Unauthorized,"user doesn't exists");
                return error;
              

            
            }
            catch (Exception e)
            {
                var error = Request.CreateResponse(HttpStatusCode.InternalServerError, e.Message);
                return error;
            }
        }
        /// <summary>
        /// Edit buyer/ seller profile details
        /// </summary>
        /// <param name="user"></param>
        /// <returns></returns>
        [HttpPost]
        [Route("EditUserDetails")]
        public HttpResponseMessage EditUserDetails(USER user)
        {
            try
            {
                var userService = new UserService();
                var success = userService.EditUserDetails(user);
                var response = Request.CreateResponse(HttpStatusCode.OK, success);
                return response;
            }
            catch (Exception e)
            {
                var error = Request.CreateResponse(HttpStatusCode.InternalServerError, e.Message);
                return error;
            }
        }
    }
}
