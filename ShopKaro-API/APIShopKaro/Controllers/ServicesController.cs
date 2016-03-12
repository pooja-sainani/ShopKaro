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
                service.ISACTIVE = true;
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

        /// <summary>
        /// Get all services for a category
        /// </summary>
        /// <param name="categoryId"></param>
        /// <returns></returns>
        [HttpGet]
        [Route("GetAllServicesByCategory/{categoryId}")]
        public HttpResponseMessage GetAllServicesByCategory(Guid? categoryId)
        {
            try
            {
                var servicesService = new ServicessService();
                var services = servicesService.GetAllServicesByCategory(categoryId);
                var response = Request.CreateResponse(HttpStatusCode.OK, services);
                return response;
            }
            catch (Exception e)
            {
                var error = Request.CreateResponse(HttpStatusCode.InternalServerError, e.Message);
                return error;
            }
        }

        /// <summary>
        /// Get service by id
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        [HttpGet]
        [Route("GetServiceById/{id}")]
        public HttpResponseMessage GetServiceById(Guid? id)
        {
            try
            {
                var servicesService = new ServicessService();
                var service = servicesService.GetServiceById(id);
                var response = Request.CreateResponse(HttpStatusCode.OK, service);
                return response;
            }
            catch (Exception e)
            {
                var error = Request.CreateResponse(HttpStatusCode.InternalServerError, e.Message);
                return error;
            }
        }

        /// <summary>
        /// Delete Service
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        [HttpPost]
        [Route("DeleteService/{id}")]
        public HttpResponseMessage DeleteService(Guid? id)
        {
            try
            {
                var servicesService = new ServicessService();
                var service = servicesService.DeleteService(id);
                var response = Request.CreateResponse(HttpStatusCode.OK, service);
                return response;
            }
            catch (Exception e)
            {
                var error = Request.CreateResponse(HttpStatusCode.InternalServerError, e.Message);
                return error;
            }
        }


        /// <summary>
        /// Get Service by Seller id
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        [HttpGet]
        [Route("GetAllServicesOfferedByMe/{id}")]
        public HttpResponseMessage GetAllServicesOfferedByMe(Guid? id)
        {
            try
            {
                var serviceService = new ServicessService();
                var services = serviceService.GetAllServicesOfferedByMe(id);
                var response = Request.CreateResponse(HttpStatusCode.OK, services);
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
