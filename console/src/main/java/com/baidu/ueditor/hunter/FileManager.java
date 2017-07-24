package com.baidu.ueditor.hunter;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.MultiState;
import com.baidu.ueditor.define.State;
import com.cgeel.common.FastDFS;
import com.cgeel.common.SpringContext;
import com.cgeel.common.mybatis.ParamBuilder;
import com.cgeel.common.mybatis.WhereBuilder;
import com.cgeel.common.mybatis.WhereCondition;
import com.cgeel.model.UeditorFile;
import com.cgeel.persistence.UeditorFileMapper;

import java.util.List;
import java.util.Map;

public class FileManager {

	private String dir = null;
	private String rootPath = null;
    private String contextPath = null;
	private String[] allowFiles = null;
    private Integer userId = null;
    private Integer userType = null;
    private String mediaDomain = null;
	private int count = 0;
	
	public FileManager ( Map<String, Object> conf ) {

		this.rootPath = (String)conf.get( "rootPath" );
        this.contextPath = (String)conf.get("contextPath");
		this.dir = PathFormat.parse(this.rootPath + (String)conf.get( "dir" ));
		//this.allowFiles = this.getAllowFiles( conf.get("allowFiles") );
        if(conf.get( "count" ) != null) {
            this.count = (Integer) conf.get("count");
        }
		userId = (Integer)conf.get("userId");
        userType = (Integer)conf.get("userType");
        mediaDomain = (String)conf.get("mediaDomain");
	}

    public State listFile(int index){
        State state = null;
        UeditorFileMapper ueditorFileMapper = SpringContext.getBean(UeditorFileMapper.class);
        ParamBuilder paramBuilder = ParamBuilder.builder();
        if(userType == 1){
            paramBuilder.put("userType", 1).put("dir", 0).put("parentId", 0);
        }else if(userType == 2){
            paramBuilder.put("userType", 2).put("userId", userId).put("dir", 0).put("parentId", 0);
        }else{
            paramBuilder.put("userType", 0).put("dir", 0).put("parentId", 0);
        }
        WhereCondition whereCondition = WhereBuilder.builder().and("userType").and("dir").and("parentId").build();
        List<UeditorFile> list = ueditorFileMapper.list(paramBuilder.build(),
                whereCondition, null, null, index, count, UeditorFile.class);
        Integer count = ueditorFileMapper.count(paramBuilder.build(), whereCondition, UeditorFile.class);
        if ( index < 0 || index > count ) {
            state = new MultiState( true );
        } else {
            state = this.getState(list);
        }

        state.putInfo( "start", index );
        state.putInfo( "total", count );

        return state;
    }

    //新增删除
    public State delFile(String path){
        State state;
        try {
            String fileId = path.replace(mediaDomain, "");
            UeditorFileMapper ueditorFileMapper = SpringContext.getBean(UeditorFileMapper.class);
            UeditorFile file = ueditorFileMapper.getBy(ParamBuilder.builder().put("path", fileId).build(),
                    WhereBuilder.builder().and("path").build(), UeditorFile.class);
            ueditorFileMapper.delete(file, WhereBuilder.builder().and("id").build());
            FastDFS.delete(fileId);
            state = new BaseState(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            state = new BaseState(false, "删除失败");
        }
        return state;
    }

    private State getState (  List<UeditorFile> list ) {

        MultiState state = new MultiState( true );
        BaseState fileState = null;

        UeditorFile file = null;

        for ( UeditorFile obj : list ) {
            if ( obj == null ) {
                break;
            }
            file = obj;
            fileState = new BaseState( true );
            fileState.putInfo( "url", mediaDomain + file.getPath() );
            state.addState( fileState );
        }

        return state;

    }
	
	/*public State listFile ( int index ) {
		
		File dir = new File( this.dir );
		State state = null;

		if ( !dir.exists() ) {
			return new BaseState( false, AppInfo.NOT_EXIST );
		}
		
		if ( !dir.isDirectory() ) {
			return new BaseState( false, AppInfo.NOT_DIRECTORY );
		}
		
		Collection<File> list = FileUtils.listFiles( dir, this.allowFiles, true );
		
		if ( index < 0 || index > list.size() ) {
			state = new MultiState( true );
		} else {
			Object[] fileList = Arrays.copyOfRange( list.toArray(), index, index + this.count );
			state = this.getState( fileList );
		}
		
		state.putInfo( "start", index );
		state.putInfo( "total", list.size() );
		
		return state;
		
	}

    //新增删除
    public State delFile(String path){
        String fileUrl = path.replace(contextPath, rootPath);
        File file = new File(fileUrl);
        State state;
        if(file.delete()){
            state = new BaseState(true, "删除成功");
        }else{
            state = new BaseState(false, "删除失败");
        }
        return state;
    }
	
	private State getState ( Object[] files ) {
		
		MultiState state = new MultiState( true );
		BaseState fileState = null;
		
		File file = null;
		
		for ( Object obj : files ) {
			if ( obj == null ) {
				break;
			}
			file = (File)obj;
			fileState = new BaseState( true );
			fileState.putInfo( "url", this.contextPath + this.getPath( file ) );
			state.addState( fileState );
		}
		
		return state;
		
	}
	
	private String getPath ( File file ) {
		
		String path = PathFormat.format(file.getAbsolutePath());
		
		return path.replace( this.rootPath, "/" );
		
	}
	
	private String[] getAllowFiles ( Object fileExt ) {
		
		String[] exts = null;
		String ext = null;
		
		if ( fileExt == null ) {
			return new String[ 0 ];
		}
		
		exts = (String[])fileExt;
		
		for ( int i = 0, len = exts.length; i < len; i++ ) {
			
			ext = exts[ i ];
			exts[ i ] = ext.replace( ".", "" );
			
		}
		
		return exts;
		
	}*/
	
}
