﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using APIShopKaro.Models;

namespace APIShopKaro.Services
{
    public class ServicessService
    {

        /// <summary>
        /// Add new service
        /// </summary>
        /// <param name="service"></param>
        /// <returns></returns>
        public Guid AddNewService(SERVICE service)
        {
            try
            {
                if (service == null)
                    throw new ArgumentNullException("Service", "Service can not be null");

                // check if all required fields are present
                if ((!service.CATEGORYID.HasValue || service.CATEGORYID.Value == Guid.Empty) || service.NAME == null ||
                    !service.PRICE.HasValue || (!service.SELLERID.HasValue || service.SELLERID.Value == Guid.Empty))
                    throw new ArgumentException("Some mandatory parameters required to add a new service are missing", "Service");

                if (!service.ID.HasValue || service.ID.Value == Guid.Empty)
                    service.ID = Guid.NewGuid();

                using (APIShopKaro.Models.apsteamCFHEntities db = new APIShopKaro.Models.apsteamCFHEntities())
                {
                    try
                    {
                        db.SERVICES.Add(service);
                        db.SaveChanges();
                    }
                    catch (System.Data.DataException e)
                    {
                        throw new Exception(e.InnerException.InnerException.Message);
                    }
                }

                return service.ID.Value;
            }
            catch (Exception e)
            {
                throw;
            }
        }

        /// <summary>
        /// Get all services for a category
        /// </summary>
        /// <param name="categoryId"></param>
        /// <returns></returns>
        public List<SERVICE> GetAllServicesByCategory(Guid? categoryId)
        {
            try
            {
                if (!categoryId.HasValue || categoryId.Value == Guid.Empty)
                    throw new ArgumentNullException("CategoryId", "CategoryId can not be null");

                List<SERVICE> services = new List<SERVICE>();

                using (APIShopKaro.Models.apsteamCFHEntities db = new APIShopKaro.Models.apsteamCFHEntities())
                {
                    try
                    {
                        services = (from p in db.SERVICES
                                 where p.CATEGORYID == categoryId
                                 select p).ToList();
                    }
                    catch (System.Data.DataException e)
                    {
                        throw new Exception(e.InnerException.InnerException.Message);
                    }
                    catch (ArgumentNullException e)
                    {                        
                    }
                }

                return services;
            }
            catch (Exception e)
            {
                throw;
            }
        }

        /// <summary>
        /// Get service by id
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        public SERVICE GetServiceById(Guid? id)
        {
            try
            {
                if (!id.HasValue || id.Value == Guid.Empty)
                    throw new ArgumentNullException("ID", "ID can not be null");

                SERVICE service;

                using (APIShopKaro.Models.apsteamCFHEntities db = new APIShopKaro.Models.apsteamCFHEntities())
                {
                    try
                    {
                        service = (from p in db.SERVICES
                                 where p.ID == id
                                 select p).Single();
                    }
                    catch (System.Data.DataException e)
                    {
                        throw new Exception(e.InnerException.InnerException.Message);
                    }
                    catch (ArgumentNullException e)
                    {
                        service = null; // no such service
                    }
                    catch(InvalidOperationException e)
                    {
                        service = null;
                    }
                }
                return service;
            }
            catch (Exception e)
            {
                throw;
            }
        }

        /// <summary>
        /// delete service
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        public bool DeleteService(Guid? id)
        {
            try
            {
                if (!id.HasValue || id.Value == Guid.Empty)
                    throw new ArgumentNullException("ID", "ID can not be null");

                using (APIShopKaro.Models.apsteamCFHEntities db = new APIShopKaro.Models.apsteamCFHEntities())
                {
                    try
                    {
                        var service = (from s in db.SERVICES
                                         where s.ID == id
                                            select s).Single();
                        db.SERVICES.Remove(service);
                        db.SaveChanges();
                        return true;
                    }
                    catch (System.Data.DataException e)
                    {
                        throw new Exception(e.InnerException.InnerException.Message);
                    }
                }
            }
            catch (Exception e)
            {
                throw;
            }
        }
    }
}