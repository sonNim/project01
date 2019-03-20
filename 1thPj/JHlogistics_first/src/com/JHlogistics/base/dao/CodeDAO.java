package com.JHlogistics.base.dao;

import java.util.ArrayList;

import com.JHlogistics.base.to.CodeTO;

public interface CodeDAO {

	public ArrayList<CodeTO> selectCodeList();

	public void insertCode(CodeTO codeTO);

	public void updateCode(CodeTO codeTO);

	public void deleteCode(CodeTO codeTO);

}
