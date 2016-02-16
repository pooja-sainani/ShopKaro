using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using APIShopKaro.Models;
using APIShopKaro.Services;

namespace APIShopKaro.Controllers
{
    [RoutePrefix("api/Services")]
    public class ServicesController : ApiController
    {
        /// <summary>
        /// Add new service
        /// </summary>
        /// <param name="service"></param>
        /// <returns></returns>
        [HttpPost]
        [Route("AddNewService")]
        public HttpResponseMessage AddNewService(SERVICE service)
        {
            try
            {
                var servicesService = new ServicessService();
                var id = servicesService.AddNewService(service);
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
