function limitLength(obj, maxLength)
  {
    if (obj.value.length > maxLength)
    {
      obj.value = obj.value.substring(0, maxLength);
    }
  }
