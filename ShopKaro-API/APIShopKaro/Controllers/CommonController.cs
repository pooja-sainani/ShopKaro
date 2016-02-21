using System;
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
    [RoutePrefix("api")]

   // [System.Web.Http.RoutePrefix("api")]
    public class CommonController : ApiController
    {
        /// <summary>
        /// Add new product/ service category
        /// </summary>
        /// <param name="category"></param>
        /// <returns></returns>
        [HttpPost]
        [Route("AddNewCategory")]
        public HttpResponseMessage AddNewCategory(CATEGORy category)
        {
            try
            {
                var commonService = new CommonService();
                var id = commonService.AddNewCategory(category);
                var response = Request.CreateResponse(HttpStatusCode.OK, id);
                return response;
            }
            catch (Exception e)
            {
                var error = Request.CreateResponse(HttpStatusCode.InternalServerError, e.Message);
                return error;
            }
        }

        /// <summary>
        /// Get all product/ service categories
        /// </summary>
        /// <param name="isService"></param>
        /// <returns></returns>
        [HttpGet]
        [Route("GetAllCategories/{isService}")]
        [ResponseType(typeof(List<CATEGORy>))]

        public HttpResponseMessage GetAllCategories(bool isService)
        {
            try
            {
                var commonService = new CommonService();
                var categories = commonService.GetAllCategories(isService);
                var response = Request.CreateResponse(HttpStatusCode.OK, categories);
                return response;
            }
            catch (Exception e)
            {
                var error = Request.CreateResponse(HttpStatusCode.InternalServerError, e.Message);
                return error;
            }
        }

        /// <summary>
        /// Add new product/ service REVIEW
        /// </summary>
        /// <param name="review"></param>
        /// <returns></returns>
        [HttpPost]
        [Route("AddNewReview")]
        public HttpResponseMessage AddNewReview(REVIEW review)
        {
            try
            {
                var commonService = new CommonService();
                var id = commonService.AddNewReview(review);
                var response = Request.CreateResponse(HttpStatusCode.OK, id);
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
